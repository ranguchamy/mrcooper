import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../auth/AuthService";
import { Form, Button, Card, Row, Col } from "react-bootstrap";

const UserRegistration = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    gender: "",
    phoneNumber: "",
    email: "",
    password: "",
    userType: "",
    roles: [],
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;

    // Auto-assign roles based on userType
    if (name === "userType") {
      const assignedRole =
        value === "CAR_OWNER" ? ["ROLE_CAR_OWNER"] : ["ROLE_CAR_USER"];
      setFormData((prevState) => ({
        ...prevState,
        [name]: value,
        roles: assignedRole, // Automatically assigns role
      }));
    } else {
      setFormData((prevState) => ({
        ...prevState,
        [name]: value,
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 🔹 Log form data before API call
    console.log(" Sending Registration Data:", formData);

    try {
      const response = await registerUser(formData);

      // 🔹 Log API response
      console.log(" Registration Successful:", response);

      alert("User registered successfully!");
      navigate("/login"); // Redirect to login after success
    } catch (error) {
      console.error("Registration failed:", error);
      alert("Registration failed! Check console for details.");
    }
  };

  return (
    <div className="container mt-4 d-flex justify-content-center">
      <Card style={{ width: "30rem" }}>
        <Card.Body>
          <Card.Title className="text-center">Register</Card.Title>
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>First Name</Form.Label>
              <Form.Control
                type="text"
                name="firstName"
                value={formData.firstName}
                onChange={handleInputChange}
                required
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                type="text"
                name="lastName"
                value={formData.lastName}
                onChange={handleInputChange}
                required
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Gender</Form.Label>
              <Form.Control
                type="text"
                name="gender"
                value={formData.gender}
                onChange={handleInputChange}
                required
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Phone Number</Form.Label>
              <Form.Control
                type="text"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleInputChange}
                required
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                required
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                required
              />
            </Form.Group>

            {/* User Type Dropdown */}
            <Form.Group as={Row} className="mb-3">
              <Col>
                <Form.Label>Account Type</Form.Label>
                <Form.Control
                  as="select"
                  name="userType"
                  required
                  value={formData.userType}
                  onChange={handleInputChange}
                >
                  <option value="">...select account type...</option>
                  <option value="CAR_OWNER">Car Owner</option>
                  <option value="CAR_USER">Car User</option>
                </Form.Control>
              </Col>
            </Form.Group>

            <Button className="mt-3 w-100" type="submit">
              Register
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
};

export default UserRegistration;
