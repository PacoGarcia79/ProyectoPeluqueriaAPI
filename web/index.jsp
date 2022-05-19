<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>API REST Peluquería</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            table {
                border:1px solid black;
                border-collapse:collapse;                
            }
            table td,
            table th {
                border:1px solid black;
                padding:5px 10px;
            }
            table th {
                background-color:orange;
            }
            .cabecera {
                font-weight: bold;
                background-color:#dddddd;
            }
            .rotulo {
                font-weight: bold;
                background-color:#f2deac;
            }
        </style>
    </head>
    <body>
        <h1>API Rest Peluquería</h1>       
        <table>
            <tr>
                <td class="rotulo">Database</td>
                <td>bdpeluqueria</td>
            </tr>                
            <tr>
                <td class="rotulo">Content-Type</td>
                <td>application/json</td>
            </tr>  
            <tr>
                <td class="rotulo">Accept</td>
                <td>application/json</td>
            </tr>  
        </table>

        <br><br> 

        <table>
            <tr>
                <th colspan="2">API REST DATA</th>
            </tr>
            <tr>
                <th>MÉTODO</th>
                <th>PATH</th>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria">./api/peluqueria</a></td>
            </tr>           
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/servicios">./api/peluqueria/servicios</a></td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/servicios/servicio</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/servicio</td>
            </tr>   
            <tr>
                <td class="cabecera">DELETE</td>
                <td>./api/peluqueria/servicios/servicio/{idServicio}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/productosgrupos">./api/peluqueria/productosgrupos</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/productos">./api/peluqueria/productos</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/productos/busqueda/{query}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/productos/{grupo}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/productos/grupo/{grupo}</td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/productos/producto</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/producto</td>
            </tr> 
            <tr>
                <td class="cabecera">DELETE</td>
                <td>./api/peluqueria/productos/producto/{idProducto}</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/productogrupo</td>
            </tr>             
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/horarios">./api/peluqueria/horarios</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/horarios/listanodisponibilidad">./api/peluqueria/horarios</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/fechas/ocupadas/{fechaComienzo}">./api/peluqueria/horarios</a></td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td><a href="./api/peluqueria/horarios/adddisponibilidad/{fechaComienzo}/{fechaFin}/{empleados}/{horas}">./api/peluqueria/horarios</a></td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td><a href="./api/peluqueria/horarios/deldisponibilidad/{fechaComienzo}/{fechaFin}/{empleados}/{horas}">./api/peluqueria/horarios</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/empleados">./api/peluqueria/empleados</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/usuarios/{idUsuario}</td>
            </tr>        
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/usuarios/usuario</td>
            </tr>            
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/empleados/empleado/fechabaja</td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/empleados/empleado/horarios</td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/usuarios/usuario/password</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/usuario</td>
            </tr> 
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/registro</td>
            </tr> 
            <tr>
                <td class="cabecera">DELETE</td>
                <td>./api/peluqueria/empleados/empleado/{idUsuario}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/peluqueria/clientes">./api/peluqueria/clientes</a></td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/horarios/libres/{fecha}</td>
            </tr> 
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/horarios/libres/empleados/{usuario}/{fecha}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/empleados/disponibles/{hora}/{fecha}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/empleados/disponibles/fecha/{fecha}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/serviciosempleados/{idEmpleado}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/citas/{fechaComienzo}/{fechaFin}/{idUsuario}</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/cita/{hora}/{empleado}/{fecha}/{cliente}/{servicios}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/citas/buscador/{horarios}/{empleados}/{fechaInicio}/{fechaFin}</td>
            </tr>
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/citas/cita/{idCita}</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/peluqueria/cita/producto/{idCita}/{idProducto}/{cantidadProducto}</td>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td>./api/peluqueria/empleados/servicio/{idServicio}</td>
            </tr>   
            <tr>
                <td class="cabecera">PUT</td>
                <td>./api/peluqueria/empleados/servicios/{idEmpleado}/{idServicio}</td>
            </tr>   
            
        </table>


        <br><br> 

        <table>
            <tr>
                <th colspan="2">API REST USER</th>
            </tr>
            <tr>
                <th>MÉTODO</th>
                <th>PATH</th>
            </tr>
            <tr>
                <td class="cabecera">GET</td>
                <td><a href="./api/auth/user">./api/auth/user</a></td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/auth/login</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/auth/logout</td>
            </tr>
            <tr>
                <td class="cabecera">POST</td>
                <td>./api/auth/destroysession</td>
            </tr>
        </table>          

        <h1>IDENTIFICACIÓN USUARIOS</h1>


        <%
            // Comprueba si se ha pulsado Desconectar para eliminar los datos de la SESSION
            String btnDesconectar = request.getParameter("btn_desconectar");
            if (btnDesconectar != null) {
                if (btnDesconectar.equals("Desconectar")) {
                    session.removeAttribute("usuario");
                }
            }

            // Recoger el valor recibido del formulario y guardarlo como Cookie de SESSION
            String iusuario = request.getParameter("iusuario");
            if (iusuario != null) {
                iusuario = iusuario.trim();
                if (!iusuario.equals("")) {
                    session.setAttribute("usuario", iusuario);
                }
            }

            String usuario;
            usuario = (String) session.getAttribute("usuario");
            if ((usuario != null) && (!usuario.equals(""))) {
        %>
        <h2>Bienvenido <span style="color:blue;"><%= usuario%></span></h2>
        <form 
            id="formUsuarios"
            name="formUsuarios" 
            method="POST"
            action="./api/auth/login.jsp" 
            >
            <input id="btn_desconectar" name="btn_desconectar" type="submit" value="Desconectar" />
        </form>
        <%  } else {%>
        <form 
            id="formUsuarios"
            name="formUsuarios" 
            >
            <table>
                <tr>
                    <th colspan="2">ACCESO USUARIOS</th>
                </tr>
                <tr>
                    <td class="cabecera">SESSION:</td>
                    <td><% out.println(session.getId()); %></td>
                </tr>
                <tr>
                    <td class="cabecera">Usuario</td>
                    <td><input id="username"   name="username" type="text" size="20"/></td>
                </tr>            
                <tr>
                    <td class="cabecera">Password</td>
                    <td><input id="password"   name="password" type="text" size="20"/></td>
                </tr>            
                <tr>
                    <td class="cabecera">&nbsp;</td>
                    <td>
                        <input id="btn_acceder" name="btn_acceder" type="submit" value="Acceder" />
                        &nbsp;
                        <input id="btn_logout" name="btn_logout" type="button" value="Logout" onclick="logout();"/>
                    </td>
                <tr>
                <tr>
                    <td colspan="2">
                         <div id="decoded">Respuesta</div>
                    </td>    
                </tr>
            </table>
            
        </form>
        
        <script>

            var formElem = document.getElementById('formUsuarios');
            formElem.onsubmit = async (e) => {
                e.preventDefault();
                var form = document.querySelector("#formUsuarios");
                // var form = document.forms[0];

                data = {
                    username: form.querySelector('input[name="username"]').value,
                    password: form.querySelector('input[name="password"]').value
                }
                 
                let response = await fetch('./api/auth/login', {
                    method: 'POST', // or 'PUT'
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })

                let text = await response.text(); // read response body as text
                document.querySelector("#decoded").innerHTML = text;
            };
            
            async function logout() {
                alert("logout");
                let response = await fetch('./api/auth/logout', {
                    method: 'POST', // or 'PUT'
                    headers: {
                        'Content-Type': 'application/json',
                    }
                })                
                
                let text = await response.text(); // read response body as text
                document.querySelector("#decoded").innerHTML = text;
            }
            
        </script>
        <%  }%>         

    </body>
</html>
