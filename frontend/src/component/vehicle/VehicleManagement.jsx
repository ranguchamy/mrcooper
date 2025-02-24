import React, { useState, useEffect } from "react";
import { fetchVehicles, addVehicle } from "./VehicleService";
import { Form, Button, Table, Card } from "react-bootstrap";

const VehicleManagement = () => {
  const ownerId = localStorage.getItem("ownerId"); // Replace with actual login ID
  const [vehicles, setVehicles] = useState([]);
  const [formData, setFormData] = useState({
    type: "Car",
    carMake: "",
    availability: true,
    rentalPrice: "",
  });

  useEffect(() => {
    async function loadVehicles() {
      const data = await fetchVehicles(ownerId);
      console.log(" Vehicles Received in UI:", JSON.stringify(data, null, 2));

      setVehicles(data);
    }
    loadVehicles();
  }, [ownerId]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log(
        "Submitting Vehicle Data:",
        JSON.stringify(formData, null, 2)
      );

      await addVehicle(formData);
      // alert("Vehicle added successfully!");

      setFormData({
        type: "Car",
        carMake: "",
        availability: true,
        rentalPrice: "",
      });

      setVehicles(await fetchVehicles(ownerId)); // Refresh list
    } catch (error) {
      console.error(" Vehicle add failed", error);
    }
  };

  return (
    <div className="container mt-4">
      <Card className="p-4">
        <h3 className="mb-3">Add Vehicle</h3>
        <Form onSubmit={handleSubmit}>
          <Form.Group>
            <Form.Label>Car Make</Form.Label>
            <Form.Control
              type="text"
              name="carMake"
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group>
            <Form.Label>Rental Price</Form.Label>
            <Form.Control
              type="number"
              name="rentalPrice"
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Button className="mt-3 w-100" type="submit">
            Add Vehicle
          </Button>
        </Form>
      </Card>

      <h3 className="mt-4">Your Vehicles</h3>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Car Make</th>
            <th>Rental Price</th>
            <th>Available</th>
          </tr>
        </thead>
        <tbody>
          {vehicles.length === 0 ? (
            <tr>
              <td colSpan="3" className="text-center">
                No vehicles listed.
              </td>
            </tr>
          ) : (
            vehicles.map((vehicle) => (
              <tr key={vehicle.id}>
                <td>{vehicle.carMake}</td>
                <td>{vehicle.rentalPrice}</td>
                <td>{vehicle.availability ? "Yes" : "No"}</td>
              </tr>
            ))
          )}
        </tbody>
      </Table>
    </div>
  );
};

export default VehicleManagement;
