import React, { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {

  // Section 1 states
  const [createUserId, setCreateUserId] = useState("");
  const [algorithm, setAlgorithm] = useState("FIXED_WINDOW");
  const [limit, setLimit] = useState("");
  const [windowSize, setWindowSize] = useState("");

  // Section 2 states
  const [requestUserId, setRequestUserId] = useState("");
  const [response, setResponse] = useState("");

  // Section 3 states
  const [statsUserId, setStatsUserId] = useState("");
  const [violations, setViolations] = useState([]);

  // -------------------------------
  // Create Rate Limit
  // -------------------------------
  const createRateLimit = async () => {
    await axios.post("http://localhost:8000/rate-limit", {
      userId: createUserId,
      algorithmType: algorithm,
      limitValue: parseInt(limit),
      windowSizeInMillis: parseInt(windowSize),
    });

    alert("Rate Limit Created");
  };

  // -------------------------------
  // Simulate Request
  // -------------------------------
  const simulateRequest = async () => {
    try {

      const res = await axios.post(
        `http://localhost:8000/rate-limit/access/${requestUserId}`
      );

      setResponse(res.data.message);

    } catch (error) {

      if (error.response && error.response.data) {
        setResponse(error.response.data.message || "Blocked");
      } else {
        setResponse("Server Error");
      }

    }
  };

  // -------------------------------
  // Fetch Stats
  // -------------------------------
  const fetchStats = async () => {

    const res = await axios.get(
      `http://localhost:8000/rate-limit/stats/${statsUserId}`
    );

    setViolations(res.data);

  };

  return (
    <div className="container">

      <h1>Rate Limiter Dashboard</h1>

      {/* ============================= */}
      {/* Section 1 - Create Rule */}
      {/* ============================= */}

      <div className="section">
        <h2>Create Rule</h2>

        <input
          placeholder="User ID"
          onChange={(e) => setCreateUserId(e.target.value)}
        />

        <select onChange={(e) => setAlgorithm(e.target.value)}>
          <option value="FIXED_WINDOW">Fixed Window</option>
          <option value="SLIDING_WINDOW">Sliding Window</option>
          <option value="TOKEN_BUCKET">Token Bucket</option>
        </select>

        <input
          placeholder="Limit"
          onChange={(e) => setLimit(e.target.value)}
        />

        <input
          placeholder="Window Size (ms)"
          onChange={(e) => setWindowSize(e.target.value)}
        />

        <button onClick={createRateLimit}>
          Create Rule
        </button>
      </div>


      {/* ============================= */}
      {/* Section 2 - Simulate Request */}
      {/* ============================= */}

      <div className="section">

        <h2>Simulate Request</h2>

        <input
          type="text"
          placeholder="Enter userId to send requests"
          onChange={(e) => setRequestUserId(e.target.value)}
        />

        <button onClick={simulateRequest}>
          Send Request
        </button>

        <p>Status: {response}</p>

      </div>


      {/* ============================= */}
      {/* Section 3 - Stats */}
      {/* ============================= */}

      <div className="section">

        <h2>Request Logs</h2>

        <input
          type="text"
          placeholder="Enter userId to fetch request stats"
          onChange={(e) => setStatsUserId(e.target.value)}
        />

        <button onClick={fetchStats}>
          Fetch Stats
        </button>

        <ul>

          {violations.map((v, index) => (

            <li key={index} className="bold">

              {v.userId} : [{v.timestamp}] : [{v.algorithm}] :

              <span className={v.allowed ? "reason-green" : "reason-red"}>

                {v.allowed ? "SUCCESS" : "RATE LIMIT EXCEEDED"}

              </span>

            </li>

          ))}

        </ul>

      </div>

    </div>
  );
}

export default App;