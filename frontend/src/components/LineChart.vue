<script>
  import { Bar } from 'vue-chartjs';

  export default {
    extends: Bar,
    props: {
      chartData: {
        type: Array,
        required: false,
      },
      chartLabels: {
        type: Array,
        required: true,
      },
    },
    data() {
      return {
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true,
              },
              gridLines: {
                display: true,
              },
            }],
            xAxes: [{
              gridLines: {
                display: false,
              },
            }],
          },
          legend: {
            display: false,
          },
          responsive: true,
          maintainAspectRatio: false,
        },
      };
    },
    mounted() {
      this.renderChart({
        labels: this.chartLabels,
        datasets: [
          {
            label: 'Total',
            pointBackgroundColor: '#ffffff',
            borderWidth: 0,
            pointBorderColor: '#222626',
            backgroundColor: '#ffffff',
            data: this.chartData,
          },
        ],
      }, this.options);
    },
  };
</script>
