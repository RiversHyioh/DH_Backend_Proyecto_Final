window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará de la nueva         
    const formulario = document.querySelector('#put_paciente');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault(); //
        let odontologoId = document.querySelector('#paciente_id').value;
       //creamos un JSON que tendrá los datos del odontologo
        const formData = {
            id: document.querySelector('#paciente_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            cedula: document.querySelector('#cedula').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                        calle: document.querySelector('#calle').value,
                        numero: document.querySelector('#numero').value,
                        localidad : document.querySelector('#localidad').value,
                        provincia: document.querySelector('#provincia').value
                        },
            email: document.querySelector('#email').value,
        };
        //invocamos utilizando la función fetch la API con el método POST que guardará
        //el odontologo que enviaremos en formato JSON
        const url = '/paciente';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
                     .then(response => response.text()) // Asegúrate de que el servidor devuelve un mensaje adecuado
                    .then(data => {
                        alert(data); // Muestra un mensaje para confirmar la actualización
                        // Limpia el formulario o realiza otras acciones necesarias
                        formulario.reset();
                        document.querySelector('#div_paciente_put').style.display = 'none';
                        // Aquí podrías actualizar la vista con la nueva información si es necesario
                    })
                    .catch(error => {
                        alert("Error: " + error);
                    });
            });
        });


    //Es la funcion que se invoca cuando se hace click sobre el id de una          del listado
    //se encarga de llenar el formulario con los datos de la         
    //que se desea modificar
    function findBy(id) {
        const url = '/paciente/buscar'+"/"+id;
        const settings = {
          method: 'GET'
        };
        fetch(url,settings)
            .then(response => response.json())
            .then(data => {
                let paciente = data;
                // Asegúrate de que paciente y paciente.domicilio existen
                 if (paciente.domicilio) {
                            document.querySelector('#paciente_id').value = paciente.id;
                            document.querySelector('#nombre').value = paciente.nombre;
                            document.querySelector('#apellido').value = paciente.apellido;
                            document.querySelector('#cedula').value = paciente.cedula;
                            document.querySelector('#fechaIngreso').value = paciente.fechaIngreso;
                            document.querySelector('#calle').value = paciente.domicilio.calle;
                            document.querySelector('#numero').value = paciente.domicilio.numero;
                            document.querySelector('#localidad').value = paciente.domicilio.localidad;
                            document.querySelector('#provincia').value = paciente.domicilio.provincia;
                            document.querySelector('#email').value = paciente.email;
                            document.querySelector('#div_paciente_put').style.display = "block";
                        } else {
                            console.error("Domicilio no encontrado en el paciente");
                        }
                    })
                    .catch(error => {
                        alert("Error: " + error);
                    });
}