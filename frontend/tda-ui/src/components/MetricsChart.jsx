import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from "chart.js";

import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

function MetricsChart({ result }) {

  // Normalize values for better chart visibility
  const maxValue = Math.max(
    result.totalFiles,
    result.totalLines,
    result.debtScore
  );

  const data = {
    labels: ["Total Files", "Total Lines", "Debt Score"],
    datasets: [
      {
        label: "Code Metrics",
        data: [
          (result.totalFiles / maxValue) * 100,
          (result.totalLines / maxValue) * 100,
          (result.debtScore / maxValue) * 100
        ],
        backgroundColor: [
          "#3498db",
          "#2ecc71",
          "#f39c12"
        ]
      }
    ]
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        labels: {
          color: "white"
        }
      },
      tooltip: {
        callbacks: {
          label: function(context) {

            const index = context.dataIndex;

            const realValues = [
              result.totalFiles,
              result.totalLines,
              result.debtScore
            ];

            return context.label + ": " + realValues[index];
          }
        }
      }
    },
    scales: {
      x: {
        ticks: {
          color: "white"
        }
      },
      y: {
        ticks: {
          color: "white"
        },
        beginAtZero: true,
        max: 100
      }
    }
  };

  return (
    <div
      style={{
        width: "700px",
        margin: "50px auto",
        background: "#1e1e1e",
        padding: "30px",
        borderRadius: "12px",
        boxShadow: "0 0 20px rgba(0,0,0,0.6)"
      }}
    >
      <h3 style={{ textAlign: "center", marginBottom: "20px" }}>
        Code Metrics
      </h3>

      <Bar data={data} options={options} />
    </div>
  );
}

export default MetricsChart;