import React, { useEffect, useState } from "react";
import TodayCard from "./components/TodayCard";
import Gallery from "./components/Gallery";
import DatePicker from "./components/DatePicker";
import DetailModal from "./components/DetailModel";

const API_BASE = import.meta.env.VITE_API_BASE || "http://localhost:8080/api/apod";

export default function App() {
  const [today, setToday] = useState(null);
  const [recent, setRecent] = useState([]);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    fetch(`${API_BASE}/today`)
      .then(r => r.json())
      .then(setToday)
      .catch(console.error);

    fetch(`${API_BASE}/recent?count=12`)
      .then(r => r.json())
      .then(data => {
        setRecent(Array.isArray(data) ? data.reverse() : []);
      })
      .catch(console.error);
  }, []);

  const onDatePick = (dateStr) => {
    fetch(`${API_BASE}?date=${dateStr}`)
      .then(r => r.json())
      .then(setSelected)
      .catch(console.error);
  };

  return (
    <div className="container">
      <header>
        <h1>NASA APOD Explorer</h1>
        <p>Explore Astronomy Picture of the Day</p>
      </header>

      <main>
        <section className="top-row">
          <TodayCard data={today} onOpenDetail={setSelected} />
          <DatePicker onPick={onDatePick} />
        </section>

        <section>
          <h2>Recent</h2>
          <Gallery items={recent} onOpen={setSelected} />
        </section>
      </main>

      {selected && <DetailModal item={selected} onClose={() => setSelected(null)} />}
      <footer>Powered by NASA APOD</footer>
    </div>
  );
}
