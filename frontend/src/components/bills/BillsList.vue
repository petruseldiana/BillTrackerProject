<template>
    <div class="list">
        <div class="list-heading">
            <div>
                <h1 class="text-white mb-5">List of all bills</h1>
            </div>
            <div class="mt-1">
                <select
                    v-model="selected"
                    class="form-control mb-2"
                    @change="getBills()"
                >
                    <option 
                        v-for="(option, index) in filterOptions"
                        :key="`option-${index}`"
                        :value="option.value"
                    >
                        {{ option.text }}
                    </option>
                </select>
                <div><button class="btn btn-primary mb-2" @click="goAddNewBill">Add new bill</button></div>
            </div>
        </div>
        <b-alert 
            variant="danger"
            dismissible
            fade
            :show="showAlert"
            @dismissed="showAlert=false"
        >
            {{ error }}
        </b-alert>
        <div class="container">
            <b-table hover striped dark :fields="tableFields" :items="items" :busy="isBusy" class="list-table">
                <template slot="commands" slot-scope="data">
                    <v-btn v-on:click="goUpdateBill(data.item.id)"><v-icon class="mr-2" name="edit" scale="1.5" /></v-btn>
                    <v-btn @click="deleteBill(data.item.id)"><v-icon class="mr-2" name="trash" scale="1.5" /></v-btn>
                    <v-btn v-if ="data.item.status==='Unpaid'" @click="updateStatus(data.item.id)"><v-icon name="check" scale="1.5" label="Bill paid"/></v-btn>
                </template>
                <div slot="table-busy" class="text-center text-danger my-2">
                    <b-spinner class="align-middle mr-2"></b-spinner>
                    <strong>Loading...</strong>
                </div>
            </b-table>
        </div>
    </div>
</template>

<script>
import AXIOS from '@/http-common';

export default {
    mounted() {
        this.getBills();
    },
    data() {
        return {
            selected: 'all',
            filterOptions: [
                {
                    value: 'all',
                    text: 'All bills',
                },
                {
                    value: 'paid',
                    text: 'Paid bills',
                },
                {
                    value: 'unpaid',
                    text: 'Unpaid bills',
                },
            ],
            isBusy: false,
            tableFields: [
                {
                    key: 'name',
                    label: 'Name',
                    sortable: true,
                },
                {
                    key: 'price',
                    label: 'Price',
                    sortable: true,
                },
                {
                    key: 'due',
                    label: 'Due date',
                    sortable: true,
                },
                {
                    key: 'details',
                    label: 'Details',
                    sortable: false,
                },
                {
                    key: 'status',
                    label: 'Status',
                    sortable: true,
                },
                {
                    key: 'creationDate',
                    label: 'Date saved',
                    sortable: true,
                },
                {
                    key: 'commands',
                    label: '',
                },
            ],
            showAlert: false,
        };
    },
    computed: {
        error() {
            return this.$store.getters.error;
        },
        items() {
            return this.$store.getters.billsList;
        },
    },
    methods: {
        toggleBusy(){
            this.isBusy = !this.isBusy;
        },
        getBills() {
            this.toggleBusy();

            if (this.selected === 'all') {
                AXIOS.get('/getAll')
                    .then((response) => {
                        this.toggleBusy();
                        this.$store.commit('setBillsList', response.data);
                        this.$store.commit('setError', null);
                        this.showAlert = false;

                    })
                    .catch((error) => {
                        this.showAlert = true;
                        this.$store.commit('setError', 'Something went wrong. Please try again.');
                    });
            }
            else {
                const status = this.selected === 'paid' 
                    ? 'true'
                    : 'false';

                AXIOS.get(`/getFilteredBills?status=${status}`)
                    .then((response) => {
                        this.toggleBusy();
                        this.$store.commit('setBillsList', response.data);
                        this.$store.commit('setError', null);
                        this.showAlert = false;
                    })
                    .catch((error) => {
                        this.showAlert = true;
                        this.$store.commit('setError', 'Something went wrong. Please try again.');
                    });
            }
        },
        goAddNewBill(){
            this.$router.push({ name: 'SaveBill' })
        },
        goUpdateBill(id){
            this.$router.push({ name: 'UpdateBill',params:{idBill:id} })
        },
        deleteBill(id){
             AXIOS.get(`/delete/${id}`)
                    .then((response) => {
                        this.getBills();
                    })
        },
        updateStatus(id){
             AXIOS.post(`/updateBillStatus/${id}`)
                    .then((response) => {
                        console.log(response.data),
                        this.getBills();
            })
        }
    },
}
</script>
