import { Route, Routes } from "react-router-dom"
import './App.css';
import HomePage from './pages/HomePage';
import Toiduaine from './pages/Toiduaine';
import Toit from './pages/Toit';
import NavigationBar from "./components/NavigationBar";

function App() {
  /// kustuta kõik
  return (
    <div className="App">
      <NavigationBar />

      <Routes>
        {/* path sees mis järgneb localhost:3000, element sees, millise faili HTMLi seal näidatakse
        localhost:3000/toit ---> Toit.jsx faili HTML ja sellega kaasnev JavaScript tuleb kaasa
        */}
        <Route path="" element={ <HomePage /> } />
        <Route path="toiduained" element={ <Toiduaine /> } />
        <Route path="toit" element={ <Toit /> } />
      </Routes>
    </div>
  );
}

export default App;
