import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../auth/AuthService";
import { Form, Button, Card } from "react-bootstrap";
import { jwtDecode } from "jwt-decode";

const Login = () => {
  const [formData, setFormData] = useState({ email: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser(formData.email, formData.password);
      console.log("Login Response: ", response);

      // Extract `data` from response
      const { token, id, roles } = response.data || {}; // Correctly access the token, id, and roles

      let userType = ""; // Define userType before the token check

      if (token) {
        // Decode the JWT token
        const decodedToken = jwtDecode(token);
        console.log("Decoded Token:", decodedToken);

        // Extract userType from roles array
        userType = roles?.[0] || decodedToken.roles?.[0];

        // Store token and user details in localStorage
        localStorage.setItem("authToken", token);
        localStorage.setItem("userType", userType);
        localStorage.setItem("ownerId", id); // Store Car Owner ID
      } else {
        console.error("No token received from login response");
      }

      if (userType === "ROLE_CAR_OWNER") {
        navigate("/vehicles");
      } else {
        navigate("/dashboard");
      }
    } catch (error) {
      console.error("Login failed", error);

      // Extract error message from backend response
      const errorMessage =
        error.response?.data?.message || "Login failed. Please try again.";

      // Show alert with the error message
      alert(errorMessage);
    }
  };

  return (
    <div className="container mt-4 d-flex justify-content-center">
      <Card style={{ width: "30rem" }}>
        <Card.Body>
          <Card.Title className="text-center">Login</Card.Title>
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="email"
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Button className="mt-3 w-100" type="submit">
              Login
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
};

export default Login;
