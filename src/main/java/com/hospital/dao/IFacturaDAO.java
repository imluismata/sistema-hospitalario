package com.hospital.dao;

import com.hospital.modelo.Factura;

import java.util.Date;
import java.util.List;

/**
 * Contrato DAO para operaciones de persistencia sobre Factura.
 */
public interface IFacturaDAO extends ICRUD<Factura> {

    /**
     * Lista facturas por estado de pago (Pendiente, Pagada, Vencida).
     *
     * @param estadoPago estado de pago a filtrar
     * @return lista de facturas del estado indicado
     */
    List<Factura> obtenerPorEstadoPago(String estadoPago);

    /**
     * Obtiene una factura asociada a una hospitalizacion.
     *
     * @param idHospitalizacion id de hospitalizacion
     * @return factura encontrada o null si no existe
     */
    Factura obtenerPorHospitalizacion(int idHospitalizacion);

    /**
     * Lista facturas emitidas dentro de un rango de fechas.
     *
     * @param fechaInicio fecha inicial del rango
     * @param fechaFin fecha final del rango
     * @return lista de facturas dentro del rango
     */
    List<Factura> obtenerPorRangoFechas(Date fechaInicio, Date fechaFin);
}
