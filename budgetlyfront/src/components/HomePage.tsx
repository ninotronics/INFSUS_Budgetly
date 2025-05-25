import React from 'react';
import { Typography } from 'antd';

const { Title } = Typography;

// Animacija "pulsirajućeg" novčića i gradient teksta
const crazyGradient = {
  background: 'linear-gradient(90deg, #42e695, #3bb2b8, #ff6ec4, #7873f5, #42e695)',
  backgroundSize: '400% 400%',
  animation: 'crazyGradient 8s ease-in-out infinite',
  WebkitBackgroundClip: 'text',
  WebkitTextFillColor: 'transparent',
  fontWeight: 700,
  fontSize: 38,
  letterSpacing: 1,
};

const effectStyle = `
@keyframes crazyGradient {
  0% {background-position:0% 50%}
  50% {background-position:100% 50%}
  100% {background-position:0% 50%}
}
@keyframes coinPulse {
  0% { transform: scale(1) rotate(0deg);}
  30% { transform: scale(1.15) rotate(-10deg);}
  50% { transform: scale(1.1) rotate(10deg);}
  70% { transform: scale(1.18) rotate(-8deg);}
  100% { transform: scale(1) rotate(0deg);}
}
.coin-anim {
  display: inline-block;
  margin-bottom: 18px;
  animation: coinPulse 2.5s infinite;
  filter: drop-shadow(0 2px 8px #42e69588);
}
`;

const CoinSVG = () => (
  <svg
    className="coin-anim"
    width="70"
    height="70"
    viewBox="0 0 70 70"
    fill="none"
    style={{ verticalAlign: 'middle' }}
  >
    <circle cx="35" cy="35" r="33" fill="#ffe066" stroke="#ffd700" strokeWidth="4" />
    <text x="50%" y="54%" textAnchor="middle" fill="#ffd700" fontSize="32" fontWeight="bold" dy=".3em">€</text>
  </svg>
);

const HomePage: React.FC = () => (
  <div style={{ textAlign: 'center', marginTop: 60, position: 'relative', minHeight: 300 }}>
    <style>{effectStyle}</style>
    <CoinSVG />
    <Title level={2} style={crazyGradient}>Dobrodošli, korisniče!</Title>
    <p style={{ fontSize: 20, marginTop: 24, color: '#3bb2b8', fontWeight: 500 }}>
      Ovdje možete upravljati svojim transakcijama i štednjama.<br />
      <span style={{ color: '#7873f5', fontWeight: 400, fontSize: 16 }}>
        Pratite svoje prihode, troškove i ciljeve štednje na jednom mjestu.
      </span>
    </p>
  </div>
);

export default HomePage;