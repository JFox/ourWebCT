/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ourwebct;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "estudiante")
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByCedula", query = "SELECT e FROM Estudiante e WHERE e.cedula = :cedula"),
    @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estudiante.findByApellidos", query = "SELECT e FROM Estudiante e WHERE e.apellidos = :apellidos")})
public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cedula")
    private Integer cedula;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Apellidos")
    private String apellidos;
    @ManyToMany(mappedBy = "estudianteCollection")
    private Collection<Curso> cursoCollection;

    public Estudiante() {
    }

    public Estudiante(Integer cedula) {
	this.cedula = cedula;
    }

    public Estudiante(Integer cedula, String nombre, String apellidos) {
	this.cedula = cedula;
	this.nombre = nombre;
	this.apellidos = apellidos;
    }

    public Integer getCedula() {
	return cedula;
    }

    public void setCedula(Integer cedula) {
	this.cedula = cedula;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellidos() {
	return apellidos;
    }

    public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
    }

    public Collection<Curso> getCursoCollection() {
	return cursoCollection;
    }

    public void setCursoCollection(Collection<Curso> cursoCollection) {
	this.cursoCollection = cursoCollection;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (cedula != null ? cedula.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Estudiante)) {
	    return false;
	}
	Estudiante other = (Estudiante) object;
	if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "ourwebct.Estudiante[cedula=" + cedula + "]";
    }

}
