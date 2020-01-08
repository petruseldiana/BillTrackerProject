<template>
    <div class="container-fluid mt-4">
        <b-alert 
            variant="danger"
            dismissible
            fade
            :show="showAlert"
            @dismissed="showAlert=false"
        >
            {{ error }}
        </b-alert>
        <div>
            <h1 class="text-white mb-2">Spendings graph</h1>
        </div>
        <div>
            <p class="text-white mb-0">Choose some dates:</p>
            <div class="form-center form-row">
                <div class="form-group mr-3">
                    <datepicker
                        input-class="form-control"
                        placeholder="Select Start Date"
                        v-model="dates.startDate"
                        :use-utc="true"
                    />
                </div>
                <div class="form-group mr-3">
                    <datepicker
                        input-class="form-control"
                        placeholder="Select End Date"
                        v-model="dates.endDate"
                        :use-utc="true"
                    />
                </div>
                <button class="btn btn-primary" @click="getByRange()">Submit</button>
            </div>
        </div>
        <div class="chart">
            <line-chart class="chart" v-if="loaded" :chart-data="records" :chart-labels="labels" />
        </div>
    </div>
</template>

<script>
import moment from 'moment';
import Datepicker from 'vuejs-datepicker';
import LineChart from '@/components/LineChart';
import AXIOS from '@/http-common';

export default {
  components: {
    Datepicker,
    LineChart,
  },
  data() {
    return {
      dates: {
        startDate: null,
        endDate: null,
      },
      showAlert: false,
      loaded: false,
      records: [],
      labels: [],
    };
  },
  computed: {
    error() {
        return this.$store.getters.error;
    },
  },
  methods: {
    formatDate(value) {
      if (value) {
        return moment(String(value)).format('YYYY-MM-DD');
      }

      return '';
    },
    getByRange() {
      this.loaded = false;

      const startDate = this.formatDate(this.dates.startDate);
      const endDate = this.formatDate(this.dates.endDate);

      AXIOS.get(`/getFilteredBillsByDate?startDate=${startDate}&endDate=${endDate}`)
        .then((response) => {
            this.records = response.data.map(record => record.prices);
            this.labels = response.data.map(record => (new Date(record.date)).toDateString());
            this.loaded = true;

            this.$store.commit('setError', null);
            this.showAlert = false;

            console.log(response.data);
        })
        .catch((error) => {
            this.showAlert = true;
            this.$store.commit('setError', 'Something went wrong. Please try again.');
        });
    },
  },
};
</script>
