import React from 'react'
import { useEffect, useState } from 'react'; 

function Toit() {

   // muutuja (mis läheb HTML), setter mis muudab muutujat = useState (mis on Reacti erikood), sulgude sees algväärtus
   const [id, setId] = useState(0); 
   const [valk, uuendaValk] = useState(0);
   const [koguarv, setKoguarv] = useState(0);
   // kui ma tahan muutujat hoida HTMLs, siis teen useState
   // setter funktsioon saab useState seest sellised omadused, mis
   //       uuendab lisaks muutujale ka HTMLi
 
   // lehele jõudmisel tehakse mingi API päring
   useEffect(() => {
     fetch("http://localhost:8080/koik-toidud") // mis aadressilt küsime
       .then(response => response.json()) // kogutagastus: headers, staatuskood 404, 400, 200
       .then(json => setKoguarv(json.length)) // reaalsed andmed, mida API otspunkt tagastab
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