import React from "react";

export default function Gallery({ items = [], onOpen }) {
  if (!items || items.length === 0) return <div>No items yet.</div>;

  return (
    <div className="gallery">
      {items.map((it) => (
        <div key={it.date} className="thumb" onClick={() => onOpen(it)}>
          {it.media_type === "image" ? (
            <img src={it.url} alt={it.title} />
          ) : (
            <div className="video-thumb">▶ Video</div>
          )}
          <div className="meta">
            <div className="title">{it.title}</div>
            <div className="date">{it.date}</div>
          </div>
        </div>
      ))}
    </div>
  );
}
