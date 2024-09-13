window.addEventListener('load', function () {
    fetch('/paciente')  // Asegúrate de que esta API devuelva una lista de pacientes
        .then(response => response.json())
        .then(data => {
            const pacienteSelect = document.getElementById('paciente_select');
            data.forEach(paciente => {
                let option = document.createElement('option');
                option.value = paciente.id;
                option.text = `${paciente.nombre} ${paciente.apellido}`;
                pacienteSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar pacientes:', error));

    // Cargar odontólogos en el select
    fetch('/odontologo')  // Asegúrate de que esta API devuelva una lista de odontólogos
        .then(response => response.json())
        .then(data => {
            const odontologoSelect = document.getElementById('odontologo_select');
            data.forEach(odontologo => {
                let option = document.createElement('option');
                option.value = odontologo.id;
                option.text = `${odontologo.nombre} ${odontologo.apellido}`;
                odontologoSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar odontólogos:', error));

    // Cargar pacientes en el select

    const formulario = document.querySelector('#add_new_turno');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = {
            paciente: {
                id: document.querySelector('#paciente_select').value // ID del paciente seleccionado
            },
            odontologo: {
                id: document.querySelector('#odontologo_select').value // ID del odontólogo seleccionado
            },
            fecha: document.querySelector('#fecha').value
        };

        fetch("/turno", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message) });
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('response').innerHTML = '<div class="alert alert-success">Turno agregado correctamente</div>';
            document.getElementById('response').style.display = 'block';
            formulario.reset();
        })
        .catch(error => {
            document.getElementById('response').innerHTML = `<div class="alert alert-danger">${error.message}</div>`;
            document.getElementById('response').style.display = 'block';
        });
    });
});
