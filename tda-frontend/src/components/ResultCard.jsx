function ResultCard({ result }) {
  return (
    <div className="result">
      <h2>Analysis Report</h2>

      <div className="metrics">
        <div className="metric-box">
          <p>Total Files</p>
          <strong>{result.totalFiles}</strong>
        </div>

        <div className="metric-box">
          <p>Total Lines</p>
          <strong>{result.totalLines}</strong>
        </div>

        <div className="metric-box">
          <p>Debt Score</p>
          <strong>{result.debtScore}</strong>
        </div>

        <div className="metric-box">
          <p>Risk Level</p>
          <strong>{result.riskLevel}</strong>
        </div>
      </div>
    </div>
  );
}

export default ResultCard;