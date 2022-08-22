package class02;

/**
 * Not implemented by zuochengyun
 *
 * 搜索二叉树的基类，其他搜索二叉树的变种可在其基础上进行扩展
 *
 * @author Ignas Lelys
 * @created Jun 29, 2011
 *
 */
public class AbstractBinarySearchTree {

	/** Root node where whole tree starts. */
	public Node root;

	/** Tree size. */
	protected int size;

	/**
	 *新建二叉树
	 *
	 * @param value
	 *            Value that node will have.
	 * @param parent
	 *            Node's parent.
	 * @param left
	 *            Node's left child.
	 * @param right
	 *            Node's right child.
	 * @return Created node instance.
	 */
	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new Node(value, parent, left, right);
	}

	/**
	 * 查找二叉树的元素信息
	 * @param element
	 *            Element value.
	 * @return Node with value provided, or null if not found.
	 */
	public Node search(int element) {
		Node node = root;
		// 首先看当前节点元素是否为空，不为空时进行后续判断
		while (node != null && node.value != null && node.value != element) {
			if (element < node.value) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return node;
	}

	/**
	 * Insert new element to tree.
	 * 首先在二叉树上找到合适的位置，然后创建新的entry并插入
	 * （当然，新插入的节点一定是树的叶子）
	 * @param element
	 *            Element to insert.
	 */
	public Node insert(int element) {
		// 看root是否为空，空则设为根节点
		if (root == null) {
			root = createNode(element, null, null, null);
			size++;
			return root;
		}

		Node insertParentNode = null;
		// 会对map做一次查找，看是否包含该元素
		Node searchTempNode = root;
		while (searchTempNode != null && searchTempNode.value != null) {
			insertParentNode = searchTempNode;
			if (element < searchTempNode.value) {
				searchTempNode = searchTempNode.left;
			} else {
				searchTempNode = searchTempNode.right;
			}
		}

		// insertParentNode为新节点要插入的位置，以insertParentNode为父节点插入
		Node newNode = createNode(element, insertParentNode, null, null);
		// insertParentNode 大于新节点，作为右孩子插入，否则作为左孩子
		if (insertParentNode.value > newNode.value) {
			insertParentNode.left = newNode;
		} else {
			insertParentNode.right = newNode;
		}

		size++;
		return newNode;
	}

	/**
	 * 如果是删除元素的话，先找到元素对应的节点，然后进行节点删除
	 *
	 * @param element
	 *            Element value to remove.
	 *
	 * @return New node that is in place of deleted node. Or null if element for
	 *         delete was not found.
	 */
	public Node delete(int element) {
		Node deleteNode = search(element);
		if (deleteNode != null) {
			return delete(deleteNode);
		} else {
			return null;
		}
	}

	/**
	 * @param deleteNode
	 *            Node that needs to be deleted.
	 *
	 * @return New node that is in place of deleted node. Or null if element for
	 *         delete was not found.
	 */
	protected Node delete(Node deleteNode) {
		// 判断当前节点是否为空
		if (deleteNode != null) {
			Node nodeToReturn = null;
			if (deleteNode != null) {
				// deleteNode 只有左孩子或右孩子时，进行直接替换
				if (deleteNode.left == null) {
					nodeToReturn = transplant(deleteNode, deleteNode.right);
				} else if (deleteNode.right == null) {
					nodeToReturn = transplant(deleteNode, deleteNode.left);
				} else {
					// deleteNode 左右孩子都不为空时，寻找deleteNode右孩子上的最小节点
					Node successorNode = getMinimum(deleteNode.right);
					// 继任节点的父节点不为deleteNode时，要将继任节点的右孩子替换到 继任节点 的位置
					if (successorNode.parent != deleteNode) {
						transplant(successorNode, successorNode.right);
						// deleteNode的右孩子节点变更为successorNode的 (新王上任继承，之前的位置由新王手下继承)
						successorNode.right = deleteNode.right;
						successorNode.right.parent = successorNode;
					}
					// 继任节点替换deleteNode，deleteNode的左孩子节点变更为successorNode的
					transplant(deleteNode, successorNode);
					successorNode.left = deleteNode.left;
					successorNode.left.parent = successorNode;
					// 变更后的节点赋值
					nodeToReturn = successorNode;
				}
				size--;
			}
			return nodeToReturn;
		}
		return null;
	}

	/**
	 * 使用 tree (newNode) 替换 (nodeToReplace).
	 *
	 * @param nodeToReplace
	 *            Node which is replaced by newNode and removed from tree.
	 * @param newNode
	 *            New node.
	 *
	 * @return New replaced node.
	 */
	private Node transplant(Node nodeToReplace, Node newNode) {
		// nodeToReplace 是根节点时，根节点变更
		if (nodeToReplace.parent == null) {
			this.root = newNode;
		// nodeToReplace 是左孩子或右孩子时
		} else if (nodeToReplace == nodeToReplace.parent.left) {
			nodeToReplace.parent.left = newNode;
		} else {
			nodeToReplace.parent.right = newNode;
		}
		// newNode的父节点指认
		if (newNode != null) {
			newNode.parent = nodeToReplace.parent;
		}
		return newNode;
	}

	/**
	 * @param element
	 * @return true if tree contains element.
	 */
	public boolean contains(int element) {
		return search(element) != null;
	}

	/**
	 * @return Minimum element in tree.
	 */
	public int getMinimum() {
		return getMinimum(root).value;
	}

	/**
	 * @return Maximum element in tree.
	 */
	public int getMaximum() {
		return getMaximum(root).value;
	}

	/**
	 * Get next element element who is bigger than provided element.
	 *
	 * @param element
	 *            Element for whom descendand element is searched
	 * @return Successor value.
	 */
	// TODO Predecessor
	public int getSuccessor(int element) {
		return getSuccessor(search(element)).value;
	}

	/**
	 * @return Number of elements in the tree.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Tree traversal with printing element values. In order method.
	 */
	public void printTreeInOrder() {
		printTreeInOrder(root);
	}

	/**
	 * Tree traversal with printing element values. Pre order method.
	 */
	public void printTreePreOrder() {
		printTreePreOrder(root);
	}

	/**
	 * Tree traversal with printing element values. Post order method.
	 */
	public void printTreePostOrder() {
		printTreePostOrder(root);
	}

	/*-------------------PRIVATE HELPER METHODS-------------------*/

	private void printTreeInOrder(Node entry) {
		if (entry != null) {
			printTreeInOrder(entry.left);
			if (entry.value != null) {
				System.out.println(entry.value);
			}
			printTreeInOrder(entry.right);
		}
	}

	private void printTreePreOrder(Node entry) {
		if (entry != null) {
			if (entry.value != null) {
				System.out.println(entry.value);
			}
			printTreeInOrder(entry.left);
			printTreeInOrder(entry.right);
		}
	}

	private void printTreePostOrder(Node entry) {
		if (entry != null) {
			printTreeInOrder(entry.left);
			printTreeInOrder(entry.right);
			if (entry.value != null) {
				System.out.println(entry.value);
			}
		}
	}

	// 最小节点，最左孩子
	protected Node getMinimum(Node node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	// 最大节点，最右孩子
	protected Node getMaximum(Node node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	// 获取下一个节点
	protected Node getSuccessor(Node node) {
		// if there is right branch, then successor is leftmost node of that
		// 如果有右孩子的话，获取右孩子的最小节点
		if (node.right != null) {
			return getMinimum(node.right);
		} else { // otherwise it is a lowest ancestor whose left child is also
			// ancestor of node
			Node currentNode = node;
			Node parentNode = node.parent;
			// 当前节点为父节点的右孩子的话，继续往上窜父节点，直到当前节点为父节点的左孩子
			while (parentNode != null && currentNode == parentNode.right) {
				// go up until we find parent that currentNode is not in right
				// subtree.
				currentNode = parentNode;
				parentNode = parentNode.parent;
			}
			// 返回父节点
			return parentNode;
		}
	}

	// -------------------------------- TREE PRINTING
	// ------------------------------------

	public void printTree() {
		printSubtree(root);
	}

	public void printSubtree(Node node) {
		if (node.right != null) {
			printTree(node.right, true, "");
		}
		printNodeValue(node);
		if (node.left != null) {
			printTree(node.left, false, "");
		}
	}

	private void printNodeValue(Node node) {
		if (node.value == null) {
			System.out.print("<null>");
		} else {
			System.out.print(node.value.toString());
		}
		System.out.println();
	}

	private void printTree(Node node, boolean isRight, String indent) {
		if (node.right != null) {
			printTree(node.right, true, indent + (isRight ? "        " : " |      "));
		}
		System.out.print(indent);
		if (isRight) {
			System.out.print(" /");
		} else {
			System.out.print(" \\");
		}
		System.out.print("----- ");
		printNodeValue(node);
		if (node.left != null) {
			printTree(node.left, false, indent + (isRight ? " |      " : "        "));
		}
	}

	public static class Node {
		public Node(Integer value, Node parent, Node left, Node right) {
			super();
			this.value = value;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		public Integer value;
		public Node parent;
		public Node left;
		public Node right;

		public boolean isLeaf() {
			return left == null && right == null;
		}

		// node的判断要对hashCode和equals重写，改为对value的判断
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			// 哈希code加上了基准值
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}
}
