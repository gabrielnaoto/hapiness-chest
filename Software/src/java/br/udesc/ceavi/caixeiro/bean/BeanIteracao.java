/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.EntregaIteracao;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Ricardo Augusto Küstner
 */
@RequestScoped
@ManagedBean(name = "beanIteracao")
public class BeanIteracao extends BeanEntity<EntregaIteracao>  implements Serializable {

    private LineChartModel lineModel1 = new LineChartModel();

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    private void createLineModels(Entrega entrega) {
        lineModel1 = initLinearModel(entrega);
        lineModel1.setTitle("Histórico Iteração");
        lineModel1.setLegendPosition("e");
        lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Iteração (x100)"));
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Tempo (Min)");
        yAxis.setMin(0);
    }

    private LineChartModel initLinearModel(Entrega entrega) {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Menor");

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Média");

        double max = 0;
        int i = 0;
        for (EntregaIteracao entregaIteracao : JDBCFactory.getDaoEntregaIteracao().getIretacaoEntrega(entrega)) {
            series1.set(i, entregaIteracao.getDistancia());
            series2.set(i, entregaIteracao.getMedia());
            i++;

            if (max < entregaIteracao.getDistancia()) {
                max = entregaIteracao.getDistancia();
            }
            if (max < entregaIteracao.getMedia()) {
                max = entregaIteracao.getMedia();
            }
        }

        lineModel1.getAxis(AxisType.Y).setMax(max * 1.1);

        model.addSeries(series1);
        model.addSeries(series2);

        return model;
    }

    @Override
    protected Persistible<EntregaIteracao> getDao() {
        return JDBCFactory.getDaoEntregaIteracao();
    }

    public void mostraGrafico(Entrega entrega) {
        createLineModels(entrega);
        openDialog("dlgGrafico");
    }

}
