/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import java.sql.Date;
import junit.framework.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author 2105461
 */
public class PersistenciaStubTest {
    
    /*:Registrar un paciente que no exite - Pos*/
    @Test
    public void registroPacienteTestUno(){        
        Date date = java.sql.Date.valueOf("1997-06-19");
        Paciente jhordy = new Paciente(1121946483,"Cedula","Jhordy Salinas",date);
        ServiciosPacientesStub servicio = new ServiciosPacientesStub();
        try{
            servicio.registrarNuevoPaciente(jhordy);
            Assert.assertTrue(true);
        }
        catch(ExcepcionServiciosPacientes e){               
            //Si entra aqui es porque no se pudo agregar exitosamente, estando mal la prueba
            Assert.fail(e.getMessage());
        }
    }
    
    /*2:Registrar un paciente ya existente - Neg*/
    @Test
    public void registroPacienteTestDos(){        
        Date date = java.sql.Date.valueOf("1997-06-25");
        Paciente repetido = new Paciente(123, "CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01"));
        //Paciente carlos= new Paciente(1178458556,"Cedula","Carlos Sanchez",date);
        ServiciosPacientesStub servicio = new ServiciosPacientesStub();
        try{
            servicio.registrarNuevoPaciente(repetido);
            Assert.fail("El paciente ya aparece en el registro");
        }
        catch(ExcepcionServiciosPacientes e){
            //Si entra aqui es porque no se pudo agregar exitosamente (Por repetir)           
            Assert.assertTrue(true);
        }
    }
    //Clase de equivalencia uno: agregar una consulta a un paciente que existe
    @Test
    public void consultaTest1(){
        ServiciosPacientesStub servicios=new ServiciosPacientesStub();
        Paciente p=new Paciente(1,"cc","Carlos Sanchez",java.sql.Date.valueOf("2000-01-01"));
        try {
            servicios.registrarNuevoPaciente(p);
            Consulta consulta=new Consulta(java.sql.Date.valueOf("2000-01-02"),"hola como estas?");
            servicios.agregarConsultaAPaciente(1,"cc",consulta);
            for(Consulta s: p.getConsultas()){
                if(s.equals(consulta)){
                    org.junit.Assert.assertTrue(s.equals(consulta));
                }
            }
        } catch (ExcepcionServiciosPacientes ex) {
            fail("No se pudo agregar la consulta a un paciente que ya existe"+ex.getMessage());   
        }
        
        
                
    }     
   //Clase de equivalencia dos:  agregar una consulta a un paciente que no existe
   @Test
    public void consultaTest2(){
        ServiciosPacientesStub servicios=new ServiciosPacientesStub();
        try {
            Consulta consulta=new Consulta(java.sql.Date.valueOf("2000-01-01"),"hola como estas?");
            servicios.agregarConsultaAPaciente(1,"cc",consulta);
            fail("Se pudo agregar la consulta de un paciente que ya existe");
        } catch (ExcepcionServiciosPacientes ex) {
            org.junit.Assert.assertTrue(true);    
        }
        
                
    }
}
