import api from "../util/api"; // Corrected Path

export async function registerUser(user) {
  try {
    const response = await api.post("/users/register", user);
    console.log("Registration Response:", response.data);
    return response.data; // Return only the response data
  } catch (error) {
    console.error("Registration Error:", error.response?.data || error.message);
    throw error;
  }
}

export async function loginUser(email, password) {
  try {
    const response = await api.post("/login", { email, password });
    console.log("Login Response Data:", response.data);

    return response.data; // Return only the response data
  } catch (error) {
    console.error("Login Error:", error.response?.data || error.message);
    throw error;
  }
}
