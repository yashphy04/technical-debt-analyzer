import { useState } from "react";
import { createProject } from "../api/projectService";
import ResultCard from "../components/ResultCard";
import "../styles/home.css";

function Home() {
  const [repoUrl, setRepoUrl] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [result, setResult] = useState(null);

  const handleAnalyze = async () => {
    if (!repoUrl) {
      setError("Repository URL is required");
      return;
    }

    try {
      setLoading(true);
      setError("");
      setResult(null);

      // 1️⃣ Create Project
      const project = await createProject(repoUrl);

      // 2️⃣ Start Analysis
      const analysisResponse = await fetch(
        `http://localhost:8080/api/projects/${project.id}/analysis`,
        { method: "POST" }
      );

      const analysis = await analysisResponse.json();

      // 3️⃣ Start Polling
      pollAnalysis(analysis.id);

    } catch (err) {
      setError("Something went wrong");
      setLoading(false);
    }
  };

  const pollAnalysis = (analysisId) => {
    const interval = setInterval(async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/analysis/${analysisId}`
        );
        const data = await response.json();

        if (data.status === "COMPLETED") {
          clearInterval(interval);
          setResult(data);
          setLoading(false);
        }

        if (data.status === "FAILED") {
          clearInterval(interval);
          setError("Analysis failed");
          setLoading(false);
        }

      } catch (err) {
        clearInterval(interval);
        setError("Polling failed");
        setLoading(false);
      }
    }, 3000); // every 3 seconds
  };

  return (
    <div className="home">
      <h1>Technical Debt Analyzer</h1>

      <div className="input-group">
        <input
          type="text"
          placeholder="Paste GitHub Repository URL"
          value={repoUrl}
          onChange={(e) => setRepoUrl(e.target.value)}
        />
        <button onClick={handleAnalyze} disabled={loading}>
          {loading ? "Analyzing..." : "Analyze"}
        </button>
      </div>

      {error && <p className="error">{error}</p>}

      {loading && <p>Cloning & Analyzing Repository...</p>}

      {result && <ResultCard result={result} />}
    </div>
  );
}

export default Home;