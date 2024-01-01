import React from 'react'
import { useEffect, useState } from 'react'; 

function Toit() {

   // muutuja (mis läheb HTML), setter mis muudab muutujat = useState (mis on Reacti erikood), sulgude sees algväärtus
   const [id, setId] = useState(0); 
   const [valk, uuendaValk] = useState(0);
   const [koguarv, setKoguarv] = useState(0);
 
   useEffect(() => {
     fetch("http://localhost:8080/koik-toidud")
       .then(response => response.json())
       .then(json => setKoguarv(json.length))
   }, []);
 
   function decreaseId() {
     if (id > 1) {
       setId(id - 1);
       fetchValkFromBackend(id - 1);
     }
   }
 
   function increaseId() {
     // id = 3
     setId(id + 1); // siin ei muudeta VEEL seda ID-d, vaid lastakse rahus klikijärgne lõpuni teha
     fetchValkFromBackend(id + 1);
   }
                               // 4
   function fetchValkFromBackend(foodId) { // id+1  4
     fetch("http://localhost:8080/toit-valgud/" + foodId)
       .then(response => response.json())
       .then(json => uuendaValk(json))
   }
 
  
  return (
    
    <div>
    <div>{koguarv}</div>
    {id > 1 && <button onClick={decreaseId}>-</button>}
    <span>ID: {id}</span>
    {id < koguarv && <button onClick={increaseId}>+</button>}
    <div>Valgukogus: {valk}</div>
  </div>
  )
}

export default Toit