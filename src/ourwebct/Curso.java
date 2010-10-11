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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "curso")
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findByNrc", query = "SELECT c FROM Curso c WHERE c.nrc = :nrc"),
    @NamedQuery(name = "Curso.findByNombre", query = "SELECT c FROM Curso c WHERE c.nombre = :nombre")})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NRC")
    private Integer nrc;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @JoinTable(name = "estudiantecurso", joinColumns = {
        @JoinColumn(name = "idCurso", referencedColumnName = "NRC")}, inverseJoinColumns = {
        @JoinColumn(name = "idEstudiante", referencedColumnName = "Cedula")})
    @ManyToMany
    private Collection<Estudiante> estudianteCollection;
    @JoinColumn(name = "Profesor", referencedColumnName = "Cedula")
    @ManyToOne(optional = false)
    private Profesor profesor;

    public Curso() {
    }

    public Curso(Integer nrc) {
	this.nrc = nrc;
    }

    public Curso(Integer nrc, String nombre) {
	this.nrc = nrc;
	this.nombre = nombre;
    }

    public Integer getNrc() {
	return nrc;
    }

    public void setNrc(Integer nrc) {
	this.nrc = nrc;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public Collection<Estudiante> getEstudianteCollection() {
	return estudianteCollection;
    }

    public void setEstudianteCollection(Collection<Estudiante> estudianteCollection) {
	this.estudianteCollection = estudianteCollection;
    }

    public Profesor getProfesor() {
	return profesor;
    }

    public void setProfesor(Profesor profesor) {
	this.profesor = profesor;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (nrc != null ? nrc.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Curso)) {
	    return false;
	}
	Curso other = (Curso) object;
	if ((this.nrc == null && other.nrc != null) || (this.nrc != null && !this.nrc.equals(other.nrc))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "ourwebct.Curso[nrc=" + nrc + "]";
    }

}
