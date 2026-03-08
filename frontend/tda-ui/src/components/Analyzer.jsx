import { useState } from "react";
import { startAnalysis, getAnalysis } from "../api/api";
import MetricsChart from "./MetricsChart";

function Analyzer() {

  const [projectId, setProjectId] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const start = async () => {

    try {

      setLoading(true);

      const res = await startAnalysis(projectId);

      const analysisId = res.data.id;

      pollResult(analysisId);

    } catch (error) {

      alert("Failed to start analysis");
      setLoading(false);

    }

  };

  const pollResult = (id) => {

    const interval = setInterval(async () => {

      const res = await getAnalysis(id);

      if (res.data.status === "COMPLETED") {

        setResult(res.data);
        setLoading(false);

        clearInterval(interval);

      }

    }, 3000);

  };

  const card = {
    background: "#1e1e1e",
    padding: "20px",
    borderRadius: "12px",
    textAlign: "center",
    boxShadow: "0 0 15px rgba(0,0,0,0.5)",
    fontSize: "20px",
    width: "220px"
  };

  const getRiskColor = (risk) => {

    if (risk === "LOW") return "#2ecc71";
    if (risk === "MEDIUM") return "#f39c12";
    if (risk === "HIGH") return "#e74c3c";

    return "white";

  };

  return (

    <div style={{ padding: "40px", fontFamily: "Arial", color: "white" }}>

      <h1>Technical Debt Analyzer</h1>

      <input
        type="text"
        placeholder="Enter Project ID"
        value={projectId}
        onChange={(e) => setProjectId(e.target.value)}
        style={{ padding: "8px", marginRight: "10px" }}
      />

      <button
        onClick={start}
        style={{
          padding: "8px 16px",
          borderRadius: "6px",
          border: "1px solid white",
          background: "transparent",
          color: "white"
        }}
      >
        Start Analysis
      </button>

      <br /><br />

      {loading && <p>Analyzing repository...</p>}

      {result && (

        <div style={{ marginTop: "40px" }}>

          <h2>Analysis Result</h2>

          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(2, 240px)",
              gap: "25px",
              marginTop: "25px"
            }}
          >

            <div style={card}>
              <h3>Total Files</h3>
              <p>{result.totalFiles}</p>
            </div>

            <div style={card}>
              <h3>Total Lines</h3>
              <p>{result.totalLines}</p>
            </div>

            <div style={card}>
              <h3>Debt Score</h3>
              <p>{result.debtScore.toFixed(2)}</p>
            </div>

            <div style={card}>
              <h3>Risk Level</h3>
              <p style={{ color: getRiskColor(result.riskLevel), fontWeight: "bold" }}>
                {result.riskLevel}
              </p>
            </div>

          </div>

          {/* Chart Section */}

          <MetricsChart result={result} />

        </div>

      )}

    </div>

  );
}

export default Analyzer;    