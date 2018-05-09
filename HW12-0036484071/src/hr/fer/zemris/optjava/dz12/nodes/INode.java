package hr.fer.zemris.optjava.dz12.nodes;

import java.util.List;

import hr.fer.zemris.optjava.dz12.models.AntWorld;

public interface INode {
	/**
	 * Vra�a dubinu stabla.
	 * 
	 * @return
	 */
	public int getDepth();

	/**
	 * Vra�a broj elemenata u stablu.
	 * 
	 * @return
	 */
	public int getSubTreeSize();

	/**
	 * Izvr�avanje stabla akcija nad zadanim svijetom.
	 * 
	 * @param world
	 * @param addToActionList
	 *            Treba li pamtiti izvrsene poteze.(potrebno samo za graficku
	 *            simulaciju.
	 */
	public void execute(AntWorld world, boolean addToActionList);

	/**
	 * Je li zavr�ni cvor.
	 * 
	 * @return
	 */
	public boolean isTerminal();

	public INode copy();

	public int childrenSize();

	public void addChild(INode node);

	public void setChild(int index, INode node);

	public List<INode> getChildren();

	/**
	 * Vra�a cvor u stablu zadanog indeksa. Obilazak je inorder.
	 * 
	 * @param index
	 * @return
	 */
	public INode getSubtree(int index);

	/**
	 * Obavlja zamjenu podstabla na zadanom indeksu sa zadanim novim podstablom.
	 * 
	 * @param index
	 * @param newSubtree
	 */
	public void replaceSubtree(int index, INode newSubtree);

}
