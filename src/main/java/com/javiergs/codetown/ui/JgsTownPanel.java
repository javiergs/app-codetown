package com.javiergs.codetown.ui;

import com.javiergs.codetown.data.JgsFileData;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Panel to show a mxGraph from files and their associations
 *
 * @version 1.0
 * @author javierGS
 */
public class JgsTownPanel extends JPanel {

  public JgsTownPanel(ArrayList<JgsFileData> files) {
    super();
    mxGraph graph = createGraph(files);
    mxGraphComponent graphPanel = new mxGraphComponent(graph);
    graphPanel.setGridVisible(true);
    graphPanel.setConnectable(false);
    setLayout(new GridLayout(1, 1));
    add(graphPanel);
  }

  private mxGraph createGraph(ArrayList<JgsFileData> jgsFileData) {
    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    graph.getModel().beginUpdate();
    Map<String, Object> edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
    edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OPEN);

    // create nodes and edges ------------------------------------------------------------------------------------------

    // get highest LOC
    if (jgsFileData == null || jgsFileData.isEmpty()) {
      return graph;
    }
    int maxLOC = 1;
    try {
      maxLOC = jgsFileData.stream().mapToInt(JgsFileData::getLinesOfCode).max().orElse(0);
    } catch (Exception e) {
      Logger.getLogger(JgsTownPanel.class.getName()).warning("error getting max LOC " + e);
    }

    // total classes

    // get width and height

    // calculate elements per row and per column considering all max LOC


    Map<String, Object> mapNodes = new HashMap<String, Object>();
    try {
      for (JgsFileData node : jgsFileData) {
        if (node == null) {
          continue;
        }
        int sigmoid = 1 / (1 + (int) Math.exp(-node.getLinesOfCode()/maxLOC));
        sigmoid = 30*sigmoid + 30;
        if (!node.hasMain()) {
          mapNodes.put(node.getName(), graph.insertVertex(parent, null, node.getName(), 0, 0, 80, sigmoid));
        } else {
          mapNodes.put(node.getName(), graph.insertVertex(parent, null, node.getName(), 0, 0, 80, sigmoid, "fillColor=orange"));
        }
      }

      for (JgsFileData node : jgsFileData) {
        if (node == null) {
          continue;
        }
        if (node.getParent() != null && !node.getParent().isEmpty() && mapNodes.containsKey(node.getParent())) {
          graph.insertEdge(parent, null, "", mapNodes.get(node.getName()), mapNodes.get(node.getParent()), "endArrow=block");
        }

        for (String association : node.getAttributes()) {
          if (mapNodes.containsKey(association)) {
            graph.insertEdge(parent, null, "", mapNodes.get(node.getName()), mapNodes.get(association), "startArrow=diamond");
          }
        }

        for (String association : node.getAssociations()) {
          if (mapNodes.containsKey(association)) {
            graph.insertEdge(parent, null, "", mapNodes.get(node.getName()), mapNodes.get(association), "endArrow=open");
          }
        }
      }

    } finally {
      graph.getModel().endUpdate();
    }
    // -----------------------------------------------------------------------------------------------------------------

    graph.setCellsEditable(false);
    graph.setCellsResizable(false);
    graph.setEnabled(false);
    graph.setConnectableEdges(false);
    graph.setAllowDanglingEdges(false);
    graph.setEdgeLabelsMovable(false);
    graph.setConnectableEdges(false);
    graph.setCellsDisconnectable(false);
    graph.setDropEnabled(false);
    //mxCircleLayout layout = new mxCircleLayout (graph);
    // mxStackLayout layout = new mxStackLayout(graph);
    // mxHierarchicalLayout layout = new mxHierarchicalLayout (graph);;
    mxFastOrganicLayout layout = new mxFastOrganicLayout (graph);
    layout.execute(graph.getDefaultParent());
    return graph;
  }

}
