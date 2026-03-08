import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api",
});

export const startAnalysis = (projectId) =>
  API.post(`/projects/${projectId}/analysis`);

export const getAnalysis = (analysisId) =>
  API.get(`/analysis/${analysisId}`);