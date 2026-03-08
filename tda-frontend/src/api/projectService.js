import axios from "axios";

const API_BASE = "http://localhost:8080/api/projects";

export const createProject = async (repoUrl) => {
  const response = await axios.post(API_BASE, {
    repositoryUrl: repoUrl,
    repositoryName: repoUrl.split("/").pop(),
  });

  return response.data;
};  