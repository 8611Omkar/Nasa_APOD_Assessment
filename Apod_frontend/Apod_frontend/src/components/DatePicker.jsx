import React, { useState } from "react";

export default function DatePicker({ onPick }) {
  const [date, setDate] = useState("");

  const submit = (e) => {
    e.preventDefault();
    if (!date) return;
    onPick(date);
  };

  return (
    <div className="card">
      <h3>Pick a date</h3>
      <form onSubmit={submit}>
        <input type="date" value={date} onChange={(e) => setDate(e.target.value)} max={new Date().toISOString().split("T")[0]} />
        <button type="submit">Load</button>
      </form>
    </div>
  );
}
