<template>
  <div class="page">
    <h2 class="text-white mb-5">Add new bill</h2>
     <b-alert 
      variant="danger"
      dismissible
      fade
      :show="showAlert"
      @dismissed="showAlert=false"
    >
      {{ error }}
    </b-alert>
    <form @submit.prevent="save">
     <div class="form-col">
        <div class="form-group col-md-12">
          <input
            v-model="bill.name"
            v-validate="{required: true}"
            type="text"
            class="form-control"
            name="billName"
            placeholder="Name"
          />
          <span class="error-message">{{ errors.first('billName') }}</span>
        </div>
        <div class="form-group col-md-12">
          <input
            v-model="bill.due"
            v-validate="{required: true}"
            type="date"
            class="form-control"
            name="billDate"
            placeholder="Due"
          />
          <span class="error-message">{{ errors.first('billDate') }}</span>
        </div>
        <div class="form-group col-md-12">
          <input
            v-model="bill.price"
            v-validate="{required: true}"
            type="number"
            class="form-control"
            name="billPrice"
            placeholder="Price"
          />
          <span class="error-message">{{ errors.first('billPrice') }}</span>
        </div>
        <div class="form-group col-md-12">
          <textarea
            v-model="bill.details"
            type="text"
            class="form-control"
            name="billDetails"
            placeholder="Details"
            rows="3"
          ></textarea>
        </div>
        <div class="form-group col-md-12">
          <input type="checkbox" id="checkbox" v-model="bill.status">
            <label for="checkbox" class="text-white pl-2">Activ</label>
          </input>
        </div>
     </div>
     <button class="btn btn-primary mx-auto">Save</button>
    </form>
  </div>
</template>

<script>
import AXIOS from '@/http-common';

export default {
    name: 'save',
    data () {
        return {
        bill: {
          name: '',
          due: null,
          status: false,
          details:'',
          price:null,
          creationDate:new Date().toISOString()
        },
        showAlert: false
    };
  },
  computed: {
    error () {
      return this.$store.getters.error;
    },
  },
  methods: {
    save(){
      this.$validator.validate()
        .then((valid) => {
          if (valid) {
            return this.callApi();
          }
        }).catch(() => {
          this.showAlert = true;
          this.$store.commit('setError', 'Please complete all the fields !');
        }); 
    },
    callApi() {
      AXIOS.post('/saveBill', this.bill)
        .then((response) => {
          this.$store.commit('setError', null);
          this.showAlert = false;
          this.$router.push({ name: 'BillsList' });
        })
        .catch((error) => {
          this.showAlert = true;
          this.$store.commit('setError', 'Something went wrong. Please try again.');
        })
    },
  },
}
</script>
