import React from "react";

export default function TodayCard({ data, onOpenDetail }) {
  if (!data) return <div className="card">Loading today's APOD...</div>;

  return (
    <div className="card">
      <h3>{data.title} — {data.date}</h3>
      {data.media_type === "image" ? (
        <img src={data.url} alt={data.title} style={{ maxWidth: "100%" }} />
      ) : (
        <iframe src={data.url} title={data.title} frameBorder="0" allowFullScreen style={{ width: "100%", height: "360px" }} />
      )}
      <p className="small">{data.copyright ? `© ${data.copyright}` : ""}</p>
      <button onClick={() => onOpenDetail(data)}>Details</button>
    </div>
  );
}
