import { Link, Route, Routes } from "react-router-dom"
import './App.css';
import HomePage from './pages/HomePage';
import Toiduaine from './pages/Toiduaine';
import Toit from './pages/Toit';

function App() {
  /// kustuta kõik
  return (
    <div className="App">
      <Link to="">
        <img className="logo" src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Salad_platter.jpg/800px-Salad_platter.jpg" alt="" />
      </Link>

      <Link to="toiduained">
        <button className="button">Toiduained</button>
      </Link>

      <Link to="toit">
        <button className="button">Toidud</button>
      </Link>
      <br /><br />

      <Routes>
        <Route path="" element={ <HomePage /> } />
        <Route path="toiduained" element={ <Toiduaine /> } />
        <Route path="toit" element={ <Toit /> } />
      </Routes>
    </div>
  );
}

export default App;
