import React, { useState, useEffect } from "react";
import { fetchAvailableVehicles } from "../vehicle/VehicleService";
import { Table, Card } from "react-bootstrap";

const Dashboard = () => {
  const [vehicles, setVehicles] = useState([]);

  useEffect(() => {
    async function loadVehicles() {
      const data = await fetchAvailableVehicles();
      setVehicles(data);
    }
    loadVehicles();
  }, []);

  return (
    <div className="container mt-4">
      <h2>🚗 Available Vehicles</h2>
      <Card className="p-4">
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
                  No vehicles available.
                </td>
              </tr>
            ) : (
              vehicles.map((vehicle) => (
                <tr key={vehicle.id}>
                  <td>{vehicle.carMake}</td>
                  <td>{vehicle.rentalPrice}</td>
                  <td>{vehicle.availability ? "✅ Yes" : "❌ No"}</td>
                </tr>
              ))
            )}
          </tbody>
        </Table>
      </Card>
    </div>
  );
};

export default Dashboard;

// // Dashboard Component
// import React from "react";

// const Dashboard = () => {
//   return (
//     <div className="container">
//       <h2>Successful Login</h2>
//       <p>Welcome to the dashboard!</p>
//     </div>
//   );
// };

// export default Dashboard;
