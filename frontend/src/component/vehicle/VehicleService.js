import api from "../util/api";

import { jwtDecode } from "jwt-decode";

export const fetchVehicles = async () => {
  try {
    const token = localStorage.getItem("authToken");
    const ownerId = localStorage.getItem("ownerId");

    if (!token) {
      console.error("❌ No auth token found in localStorage!");
      return;
    }
    if (!ownerId) {
      console.error("❌ No owner ID found in localStorage!");
      return;
    }

    // ✅ Decode the JWT and check the roles
    const decodedToken = jwtDecode(token);
    console.log("✅ Decoded Token:", decodedToken);
    console.log("✅ User Roles:", decodedToken.roles); // 🔥 Check if roles are included

    const fullUrl = `${api.defaults.baseURL}/vehicles/${ownerId}`;
    console.log("🚀 Full Request URL:", fullUrl);

    const response = await api.get(`/vehicles/${ownerId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    console.log(
      "Vehicles Received in UI:",
      JSON.stringify(response.data, null, 2)
    ); //Log received vehicles

    console.log(" Response Data:", response.data);
    return response.data;
  } catch (error) {
    if (error.response) {
      console.error(
        " Server Response:",
        error.response.status,
        error.response.data
      );
    } else {
      console.error("Error fetching vehicles:", error.message);
    }
    return [];
  }
};
export const addVehicle = async (vehicle) => {
  try {
    const token = localStorage.getItem("authToken");
    const ownerId = localStorage.getItem("ownerId"); //Ensure ownerId is retrieved

    console.log(
      " Sending Vehicle Data to Backend:",
      JSON.stringify(vehicle, null, 2)
    );

    const response = await api.post(`/vehicles/${ownerId}`, vehicle, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    console.log(" Vehicle Successfully Added:", response.data);
    return response.data;
  } catch (error) {
    console.error(
      " Error adding vehicle:",
      error.response ? error.response.data : error
    );
    throw error;
  }
};

export const fetchAvailableVehicles = async () => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("No auth token found in localStorage!");
      return [];
    }

    const response = await api.get(`/vehicles/available`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    console.log(
      "✅ Available Vehicles:",
      JSON.stringify(response.data, null, 2)
    );
    return response.data;
  } catch (error) {
    console.error(" Error fetching available vehicles:", error);
    return [];
  }
};
