import React, { useEffect, useRef, useState } from 'react'

function Toiduaine() {
  const [koikValgud, setValgud] = useState(0);
  const [koikRasvad, setRasvad] = useState(0);
  const [koikToiduained, setToiduained] = useState([]);
  const nimiRef = useRef();
  const valkRef = useRef();
  const rasvRef = useRef();
  const sysivesikRef = useRef();

  useEffect(() => {
    fetch("http://localhost:8080/koik-valgud")
      .then(vastus => vastus.json())
      .then(tagastus => setValgud(tagastus));
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/koik-rasvad")
      .then(res => res.json())
      .then(json => setRasvad(json));
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/toiduained")
      .then(res => res.json())
      .then(json => setToiduained(json));
  }, []);

  const kustutaToiduaine = (nimi) => {
    fetch("http://localhost:8080/kustuta-toiduaine/" + nimi)
      .then(res => res.json())
      .then(json => setToiduained(json));
  }

  const lisaToiduaine = () => {
    fetch("http://localhost:8080/lisa-toiduaine2?nimi=" + nimiRef.current.value + 
      "&rasv=" + rasvRef.current.value +
      "&valk=" + valkRef.current.value + 
      "&sysivesik=" + sysivesikRef.current.value)
      .then(res => res.json())
      .then(json => setToiduained(json));
  }

  return (
    <div>
      <div>Kõikide toiduainete valgud kokku: {koikValgud}</div>
      <div>Kõikide toiduainete rasvad kokku: {koikRasvad}</div>
         {/* {"nimi":"vorst","rasv":10,"sysivesik":4,"valk":20} => <div>vorst</div> */}
         {/* {"nimi":"hapukoor","rasv":10,"sysivesik":8,"valk":10} => <div>hapukoor</div> */}
      <table>
        <thead>
          <tr>
            <th>Nimi</th>
            <th>Valk</th>
            <th>Süsivesik</th>
            <th>Rasv</th>
            <th>Kustuta</th>
          </tr>
        </thead>
        <tbody>{koikToiduained.map(toiduaine => 
          <tr key={toiduaine.nimi}>
            <td>{toiduaine.nimi}</td>
            <td>{toiduaine.valk}</td>
            <td>{toiduaine.sysivesik}</td>
            <td>{toiduaine.rasv}</td>
            <td><button onClick={() => kustutaToiduaine(toiduaine.nimi)}>x</button></td>
          </tr>)}
        </tbody>
      </table>

      <label>Toiduaine nimi</label> <br />
      <input ref={nimiRef} type="text" /> <br />
      <label>Toiduaine valk</label> <br />
      <input ref={valkRef} type="number" /> <br />
      <label>Toiduaine süsivesik</label> <br />
      <input ref={sysivesikRef} type="number" /> <br />
      <label>Toiduaine rasv</label> <br />
      <input ref={rasvRef} type="number" /> <br />
      <button onClick={lisaToiduaine}>Sisesta</button> <br />

    </div>
  )
}

export default Toiduaine