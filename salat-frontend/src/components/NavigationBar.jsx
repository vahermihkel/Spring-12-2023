import { Link } from "react-router-dom"

function NavigationBar() {
  return (
    <div>
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
    </div>
  )
}

export default NavigationBar