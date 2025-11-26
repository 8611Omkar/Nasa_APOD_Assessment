import React from "react";

export default function DetailModal({ item, onClose }) {
  if (!item) return null;
  return (
    <div className="modal-backdrop">
      <div className="modal">
        <button className="close" onClick={onClose}>Close</button>
        <h2>{item.title} — {item.date}</h2>
        {item.media_type === "image" ? (
          <img src={item.hdurl || item.url} alt={item.title} style={{ maxWidth: "100%" }} />
        ) : (
          <iframe src={item.url} title={item.title} frameBorder="0" allowFullScreen style={{ width: "100%", height: "420px" }} />
        )}
        <p>{item.explanation}</p>
        <p className="small">{item.copyright ? `© ${item.copyright}` : ""}</p>
      </div>
    </div>
  );
}
