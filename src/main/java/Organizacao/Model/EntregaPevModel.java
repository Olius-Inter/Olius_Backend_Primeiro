package Organizacao.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

// Entrega de óleo feita por um cliente B2C em um ponto de entrega (PEV)
public class EntregaPevModel {

    private int idEntrega;
    private LocalDate dtEntrega;
    private BigDecimal volumeEntrega; // em litros
    private int idPev;
    private int idB2c;

    public EntregaPevModel(LocalDate dtEntrega, BigDecimal volumeEntrega, int idPev, int idB2c) {
        this.dtEntrega = dtEntrega;
        this.volumeEntrega = volumeEntrega;
        this.idPev = idPev;
        this.idB2c = idB2c;
    }

    public EntregaPevModel(int idEntrega, LocalDate dtEntrega, BigDecimal volumeEntrega, int idPev, int idB2c) {
        this(dtEntrega, volumeEntrega, idPev, idB2c);
        this.idEntrega = idEntrega;
    }

    public int getIdEntrega() { return idEntrega; }
    public void setIdEntrega(int idEntrega) { this.idEntrega = idEntrega; }
    public LocalDate getDtEntrega() { return dtEntrega; }
    public void setDtEntrega(LocalDate dtEntrega) { this.dtEntrega = dtEntrega; }
    public BigDecimal getVolumeEntrega() { return volumeEntrega; }
    public void setVolumeEntrega(BigDecimal volumeEntrega) { this.volumeEntrega = volumeEntrega; }
    public int getIdPev() { return idPev; }
    public void setIdPev(int idPev) { this.idPev = idPev; }
    public int getIdB2c() { return idB2c; }
    public void setIdB2c(int idB2c) { this.idB2c = idB2c; }
}
