package main;

import graphe.TestDijkstraAlgorithm;
import io.AffichageSimulation;
import io.AffichageStart;
import simulation.TestSimulation;
import espace.Espace;
import espace.MoteurEspace;
import espace.TestEspace;
import foule.TestFoule;
import foule.TestIndividu;

public class Test {
	public static void tests() {
		
		//////////////////////////////////////////////////////////////////////////////////////
		// Tests Dijkstra
		
		/*
		TestDijkstraAlgorithm testDijkstra = new TestDijkstraAlgorithm();
		testDijkstra.testGetShortestPath();
		*/
				
		//////////////////////////////////////////////////////////////////////////////////////
		// Tests Espace
		
		//TestEspace.testEspace();
		//TestEspace.testFindPositionsObstaclesVoisins();
		//TestEspace.testPointsInteretObstacle();
		//TestEspace.testFindPointsInteret();
		//TestEspace.testConstruireGrapheAssocie();
		
		//////////////////////////////////////////////////////////////////////////////////////
		//Test sur individu
		
		//TestIndividu.testFindChemin();
		
		//////////////////////////////////////////////////////////////////////////////////////
		//Tests Foule

		//TestFoule.testFoule();
		
		//////////////////////////////////////////////////////////////////////////////////////
		// Tests de simulation
		
		//TestSimulation.testSimulationSansCollision();
		//TestSimulation.testSimulationAvecCollision();		
		//TestSimulation.testSimulationSimple();
		//TestSimulation.testFinDeSimulation();
		
		//////////////////////////////////////////////////////////////////////////////////////
		// Tests interface graphique
		
		/*
		AffichageStart frame = new AffichageStart();
		frame.setVisible(true);
		*/
		
	}
}
