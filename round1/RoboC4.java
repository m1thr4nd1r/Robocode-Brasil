package orion.round1;

import java.awt.Color;
import java.util.*;

import robocode.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Cerebro - a robot by (your name here)
 */
public class RoboC4 extends Robot {
	boolean acertei, atingido, mov1, mov2, mov3;
	int contBalasRecebidas = 0,
		contBalasAcertos = 0,
		contBalasAtiradas = 0,
		contBalasErradas = 0;
	Informacoes informacoes;

	/**
	 * run: Cerebro's default behavior
	 */
	public void run() {
		init();
		Cerebro cerebro = new Cerebro(0, 0);

		// Robot main loop
		while(true) {
			/*Preencho meu objeto informações com as informações coletadas pelos eventos. */
			informacoes.setQuantBalasAcertas(contBalasAcertos);
			informacoes.setQuantBalasRecebidas(contBalasRecebidas);
			informacoes.setQuantBalasErradas(contBalasErradas);
			informacoes.setQuantBalasAtiradas(contBalasAtiradas);
			/* Condições para mudança de movimento.
			 * A regra consiste em alterar o movimento e obedecendo a ordem dos "ifs". Caso
			 * não seja decessário mudanças, ele irá entrar na condição do seu movimento atual. */
			if((cerebro.mudarMovimentoByInformacoes(informacoes) && !mov1) || (!cerebro.mudarMovimentoByInformacoes(informacoes) && mov1)){
				movimentarQuad(Cerebro.DIREITA_SUPERIOR, Cerebro.DIREITA_INFERIOR, Cerebro.ESQUERDA_INFERIOR, Cerebro.ESQUERDA_SUPERIOR);
				mov1 = true;
				mov2 = false;
				mov3 = false;
			}else if((cerebro.mudarMovimentoByInformacoes(informacoes) && !mov2) || (!cerebro.mudarMovimentoByInformacoes(informacoes) && mov2)){//			
				movimentar(Cerebro.TOPO, Cerebro.ESQUERDA, Cerebro.BAIXO, Cerebro.DIREITA);
				mov1 = false;
				mov2 = true;
				mov3 = false;
			}else if((cerebro.mudarMovimentoByInformacoes(informacoes) && !mov3) || (!cerebro.mudarMovimentoByInformacoes(informacoes) && mov3)){
				movimentar(Cerebro.DIREITA, Cerebro.BAIXO, Cerebro.ESQUERDA, Cerebro.TOPO);
				mov1 = false;
				mov2 = false;
				mov3 = true;
			}else{
				//Primeiro movimento.
				movimentar(Cerebro.TOPO, Cerebro.ESQUERDA, Cerebro.BAIXO, Cerebro.DIREITA);
			}
		}
	}

	private void init() {
		/*Inicializando os atributos*/
		acertei = false;
		atingido = false;
		mov1 = false;
		mov2 = false;
		mov3 = false;
		informacoes = new Informacoes();

		// Inicializar cores
		 setBodyColor(Color.red);
		 setGunColor(Color.gray);
		 setRadarColor(Color.orange);
		 setScanColor(Color.yellow);
		 setBulletColor(Color.yellow);
	}

	public void radar() {
		turnGunRight(3600000000000000L);
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		if (acertei) {
			if (getEnergy() > 90 || e.getEnergy() < 30) {
				fire(5);
			} else {
				fire(1);
			}
			scan();
		} else {
			fire(1);
		}
		contBalasAtiradas++;
	}

	public void onHitByBullet(HitByBulletEvent e) {
		contBalasRecebidas++;
	}
	
	public void onHitWall(HitWallEvent e) {
		acertei = false;
		contBalasErradas++;
	}
	
	public void onBulletHit(BulletHitEvent event) {
		acertei = true;
		contBalasAcertos++;
	}

	public void movimentar(String primeiraPos, String segundaPos, String terceiraPos, String quartaPos){
		Random rand = new Random();
		Cerebro cerebro = new Cerebro(getBattleFieldWidth(), getBattleFieldHeight());
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosMedios().get(primeiraPos).getY(), 
			getX(), 
			cerebro.obterPontosMedios().get(primeiraPos).getX()));		
		turnRight((1 + rand.nextInt(9)) * 10 );
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosMedios().get(segundaPos).getY(), 
			getX(), 
			cerebro.obterPontosMedios().get(segundaPos).getX()));		
		turnRight(rand.nextInt((1 + rand.nextInt(9)) * 10));
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosMedios().get(terceiraPos).getY(), 
			getX(), 
			cerebro.obterPontosMedios().get(terceiraPos).getX()));
		turnRight(rand.nextInt((1 + rand.nextInt(9)) * 10));
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosMedios().get(quartaPos).getY(), 
			getX(), 
			cerebro.obterPontosMedios().get(quartaPos).getX()));
		turnRight(rand.nextInt((1 + rand.nextInt(9)) * 10));

	}
	
	public void movimentarQuad(String primeiraPos, String segundaPos, String terceiraPos, String quartaPos){
		Cerebro cerebro = new Cerebro(getBattleFieldWidth(), getBattleFieldHeight());		
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosLaterais().get(primeiraPos).getY(), 
			getX(), 
			cerebro.obterPontosLaterais().get(primeiraPos).getX()));
		turnRight(90);
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosLaterais().get(segundaPos).getY(), 
			getX(), 
			cerebro.obterPontosLaterais().get(segundaPos).getX()));		
		turnRight(90);
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosLaterais().get(terceiraPos).getY(), 
			getX(), 
			cerebro.obterPontosLaterais().get(terceiraPos).getX()));
		turnRight(90);
		ahead(cerebro.obterDistanciaEntreDoisPontos(getY(), 
			cerebro.obterPontosLaterais().get(quartaPos).getY(), 
			getX(), 
			cerebro.obterPontosLaterais().get(quartaPos).getX()));
		turnRight(90);
	}

	private class Informacoes {
		private int quantBalasErradas;
		private int quantBalasRecebidas;
		private int quantBalasAcertos;
		private int quantBalasAtiradas;
		
		public int getQuantBalasRecebidas() {
			return quantBalasRecebidas;
		}
		public void setQuantBalasRecebidas(int quantBalasRecebidas) {
			this.quantBalasRecebidas = quantBalasRecebidas;
		}
		public int getQuantBalasAcertos() {
			return quantBalasAcertos;
		}
		public void setQuantBalasAcertas(int quantBalasAcertos) {
			this.quantBalasAcertos = quantBalasAcertos;
		}
		public int getQuantBalasErradas() {
			return quantBalasErradas;
		}
		public void setQuantBalasErradas(int quantBalasErradas) {
			this.quantBalasErradas = quantBalasErradas;
		}
		public int getQuantBalasAtiradas() {
			return quantBalasAtiradas;
		}
		public void setQuantBalasAtiradas(int quantBalasAtiradas) {
			this.quantBalasAtiradas = quantBalasAtiradas;
		}
	}
	
	private class Coordenadas {
		private double x;
		private double y;
		
		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}
	}
	
	private class Cerebro {
		public static final String TOPO = "topo";
		public static final String DIREITA = "direita";
		public static final String ESQUERDA = "esquerda";
		public static final String BAIXO = "baixo";
		public static final String DIREITA_SUPERIOR = "direitaSuperior";
		public static final String DIREITA_INFERIOR = "direitaInferior";
		public static final String ESQUERDA_SUPERIOR = "esquerdaSuperior";
		public static final String ESQUERDA_INFERIOR = "esquerdaInferior";
		
		
		private double widthBattleField, heightBattleField;
	
		public Cerebro(double widthBattleField, double heightBattleField){
			this.heightBattleField = heightBattleField;
			this.widthBattleField = widthBattleField;
		}
	
		public  boolean proximoParede(double x, double y){
			
			if((y > heightBattleField - 50) || (x > widthBattleField - 50) ||  (y < 50) || (x < 50)){
				return true;
			}else if((x < widthBattleField - 50) && (y < 50)){ //Inferior direita
				return true;
			}else if(y <  50 && x < 50){ //Inferior esquerda
				return true;
			}else if((y > heightBattleField - 50) && (x < 50)){//Superior esquerda
				return true;
			}else if((x > widthBattleField - 50) && (y > heightBattleField - 50)){
				return true;
			}
			
			return false;
		}
		
		public double obterDistanciaEntreDoisPontos(double yOrigem, double yDestino, double xOrigem, double xDestino){
			return Math.sqrt(Math.pow((xDestino - xOrigem), 2) + Math.pow((yDestino - yOrigem),2)); 
		}
		
		public Map<String, Coordenadas> obterPontosMedios() {
			
			Map<String, Coordenadas> pontosMedioBattleField = new HashMap<String, Coordenadas>();
			
			Coordenadas cEsquerda = null, cDireita = null, cTopo = null, cBaixo = null;
			cEsquerda = new Coordenadas();
			cDireita = new Coordenadas();
			cTopo = new Coordenadas();
			cBaixo = new Coordenadas();
			
			//Considere 800x600
			//(0,300)
			cEsquerda.setX(0);
			cEsquerda.setY(heightBattleField/2);		
			//(800,300)
			cDireita.setX(widthBattleField);
			cDireita.setY(heightBattleField/2);		
			//(400,600)
			cTopo.setX(widthBattleField/2);
			cTopo.setY(heightBattleField);
			//(400,0);
			cBaixo.setX(widthBattleField/2);
			cBaixo.setY(0);
			
			pontosMedioBattleField.put(ESQUERDA, cEsquerda);
			pontosMedioBattleField.put(DIREITA, cDireita);
			pontosMedioBattleField.put(TOPO, cTopo);
			pontosMedioBattleField.put(BAIXO, cBaixo);
			
			return pontosMedioBattleField;
		}
		
		public Map<String, Coordenadas> obterPontosLaterais() {
		
			Map<String, Coordenadas> pontosLateraisBattleField = new HashMap<String, Coordenadas>();
			
			Coordenadas cEsquerdaSup = null, cDireitaSup = null, cDireitaInf = null, cEsquerdaInf = null;
			cEsquerdaSup = new Coordenadas();
			cDireitaSup = new Coordenadas();
			cDireitaInf = new Coordenadas();
			cEsquerdaInf = new Coordenadas();
			
			//Considere 800x600
			//(0,600)
			cEsquerdaSup.setX(0);
			cEsquerdaSup.setY(heightBattleField);		
			//(800,600)
			cDireitaSup.setX(widthBattleField);
			cDireitaSup.setY(heightBattleField);		
			//(800,0)
			cDireitaInf.setX(widthBattleField);
			cDireitaInf.setY(0);
			//(0,0);
			cEsquerdaInf.setX(0);
			cEsquerdaInf.setY(0);
			
			pontosLateraisBattleField.put(ESQUERDA_SUPERIOR, cEsquerdaSup);
			pontosLateraisBattleField.put(DIREITA_SUPERIOR, cDireitaSup);
			pontosLateraisBattleField.put(DIREITA_INFERIOR, cDireitaInf);
			pontosLateraisBattleField.put(ESQUERDA_INFERIOR, cEsquerdaInf);
			
			return pontosLateraisBattleField;
		}
		
		public String sorteiaPosicaoCampoBatalha() {
		   List<String> lista = new ArrayList<String> () ;
		     
		    lista.add(TOPO);
		    lista.add(BAIXO);
		    lista.add(DIREITA);
		    lista.add(ESQUERDA);
		 
		    Collections.shuffle(lista) ;
		
		    // pega qualquer indice. pegamos o primeiro para conveniencia.
		    return lista.get(0);
		}
		
		public boolean mudarMovimentoByInformacoes(Informacoes informacoes) {
			
			if (informacoes.getQuantBalasAcertos() < informacoes.getQuantBalasRecebidas()
					|| informacoes.getQuantBalasAcertos() < informacoes.getQuantBalasErradas()) {
				return true;
			}
			
			return false;
		}
	}
}
