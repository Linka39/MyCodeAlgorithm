package class02;

/**
 * Not implemented by zuochengyun
 *
 * 1、红黑树的特性
 * 具体来说，红黑树是满足如下条件的二叉查找树（binary search tree）：
 *       性质1. 节点是红色或黑色。
 *       性质2. 根节点是黑色。
 *       性质3. 每个叶节点(NIL节点，空节点)是黑色的。
 *       性质4. 每个红色节点的两个子节点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色节点)
 *       性质5. 从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。
 * 注针对性质3：nil是一个对象值，如果要把一个对象设置为空的时候就用nil。
 *
 * 在树的结构发生改变（插入或者删除操作），往往会破坏上述条件3或条件4，需要通过调整使得查找树重新满足红黑树的条件。
 * 详细参考 https://blog.csdn.net/zhangvalue/article/details/101483379?ops_request_misc=&request_id=&biz_id=102&utm_term=%E7%BA%A2%E9%BB%91%E6%A0%91&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-101483379.142
 * @author Ignas Lelys
 * @created May 6, 2011
 *
 */
public class RedBlackTree extends AbstractSelfBalancingBinarySearchTree {

    protected enum ColorEnum {
        RED,
        BLACK
    };

    // 定义空对象nil
    protected static final RedBlackNode nilNode = new RedBlackNode(null, null, null, null, ColorEnum.BLACK);


    //getEntry()方法, hashmap中查找key的方法，实际和搜索二叉树相同
//    final Entry<K,V> getEntry(Object key) {
//        if (key == null)//不允许key值为null
//            throw new NullPointerException();
//        Comparable<? super K> k = (Comparable<? super K>) key;//使用元素的自然顺序
//        Entry<K,V> p = root;
//        while (p != null) {
//            int cmp = k.compareTo(p.key);
//            if (cmp < 0)//向左找
//                p = p.left;
//            else if (cmp > 0)//向右找
//                p = p.right;
//            else
//                return p;
//        }
//        return null;
//    }

    /**
     */
    @Override
    public Node insert(int element) {
        Node newNode = super.insert(element);
        newNode.left = nilNode;
        newNode.right = nilNode;
        root.parent = nilNode;
        // 红黑树调整，调整往往需要1.改变某些节点的颜色，2.对某些节点进行旋转
        insertRBFixup((RedBlackNode) newNode);
        return newNode;
    }

    /**
     * Slightly modified delete routine for red-black tree.
     *
     * {@inheritDoc}
     */
    @Override
    protected Node delete(Node deleteNode) {
        Node replaceNode = null; // track node that replaces removedOrMovedNode
        if (deleteNode != null && deleteNode != nilNode) {
            Node removedOrMovedNode = deleteNode; // same as deleteNode if it has only one child, and otherwise it replaces deleteNode
            ColorEnum removedOrMovedNodeColor = ((RedBlackNode)removedOrMovedNode).color;  // 获取待删除节点的颜色

            // 如果待删除节点只有一个孩子节点时，直接用孩子节点将其替换
            if (deleteNode.left == nilNode) {
                replaceNode = deleteNode.right;
                rbTreeTransplant(deleteNode, deleteNode.right);
            } else if (deleteNode.right == nilNode) {
                replaceNode = deleteNode.left;
                rbTreeTransplant(deleteNode, deleteNode.left);
            } else {
                removedOrMovedNode = getMinimum(deleteNode.right);
                removedOrMovedNodeColor = ((RedBlackNode)removedOrMovedNode).color;
                replaceNode = removedOrMovedNode.right;
                if (removedOrMovedNode.parent == deleteNode) {
                    replaceNode.parent = removedOrMovedNode;
                } else {
                    rbTreeTransplant(removedOrMovedNode, removedOrMovedNode.right);
                    removedOrMovedNode.right = deleteNode.right;
                    removedOrMovedNode.right.parent = removedOrMovedNode;
                }
                rbTreeTransplant(deleteNode, removedOrMovedNode);
                removedOrMovedNode.left = deleteNode.left;
                removedOrMovedNode.left.parent = removedOrMovedNode;
                ((RedBlackNode)removedOrMovedNode).color = ((RedBlackNode)deleteNode).color;
            }

            // 长度减一，进行红黑树调整
            size--;
            if (removedOrMovedNodeColor == ColorEnum.BLACK) {
                deleteRBFixup((RedBlackNode)replaceNode);
            }
        }

        return replaceNode;
    }

    /**
    */
    @Override
    protected Node createNode(int value, Node parent, Node left, Node right) {
        return new RedBlackNode(value, parent, left, right, ColorEnum.RED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node getMinimum(Node node) {
        while (node.left != nilNode) {
            node = node.left;
        }
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node getMaximum(Node node) {
        while (node.right != nilNode) {
            node = node.right;
        }
        return node;
    }

    /**
     * TreeMap中左旋代码如下，左旋后右孩子为node的父节点
     * {@inheritDoc}
     */
    @Override
    protected Node rotateLeft(Node node) {
        // 右节点认父，接入node位置
        Node temp = node.right;
        temp.parent = node.parent;

        // node的右孩子接入，前右孩子（要上位节点）的左孩子节点（保证大于node）
        node.right = temp.left;
        // 新接入的节点把node设为父节点
        if (node.right != nilNode) {
            node.right.parent = node;
        }

        temp.left = node;
        node.parent = temp;

        // 前右孩子看下父节点是否为空，
        if (temp.parent != nilNode) {
            // 不为空的话，看node之前在父节点是左孩子还是右孩子，并做相应替换
            if (node == temp.parent.left) {
                temp.parent.left = temp;
            } else {
                temp.parent.right = temp;
            }
        // 空的话将其设为root
        } else {
            root = temp;
        }

        return temp;
    }

    /**
     * TreeMap中右旋代码如下，右旋后左孩子为node的父节点
     * {@inheritDoc}
     */
    @Override
    protected Node rotateRight(Node node) {
        // 左节点认父，接入node位置
        Node temp = node.left;
        temp.parent = node.parent;

        // node的左孩子接入，前左孩子的右子节点
        node.left = temp.right;
        if (node.left != nilNode) {
            node.left.parent = node;
        }

        temp.right = node;
        node.parent = temp;

        // temp took over node's place so now its parent should point to temp
        if (temp.parent != nilNode) {
            if (node == temp.parent.left) {
                temp.parent.left = temp;
            } else {
                temp.parent.right = temp;
            }
        } else {
            root = temp;
        }

        return temp;
    }


    /**
     * 和二叉搜索树相同（交换节点）
     * Similar to original transplant() method in BST but uses nilNode instead of null.
     */
    private Node rbTreeTransplant(Node nodeToReplace, Node newNode) {
        if (nodeToReplace.parent == nilNode) {
            this.root = newNode;
        } else if (nodeToReplace == nodeToReplace.parent.left) {
            nodeToReplace.parent.left = newNode;
        } else {
            nodeToReplace.parent.right = newNode;
        }
        newNode.parent = nodeToReplace.parent;
        return newNode;
    }

    /**
     * Restores Red-Black tree properties after delete if needed.
     */
    private void deleteRBFixup(RedBlackNode x) {
        while (x != root && isBlack(x)) {

            if (x == x.parent.left) {
                RedBlackNode w = (RedBlackNode)x.parent.right;
                if (isRed(w)) { // case 1 - sibling is red
                    w.color = ColorEnum.BLACK;
                    ((RedBlackNode)x.parent).color = ColorEnum.RED;
                    rotateLeft(x.parent);
                    w = (RedBlackNode)x.parent.right; // converted to case 2, 3 or 4
                }
                // case 2 sibling is black and both of its children are black
                if (isBlack(w.left) && isBlack(w.right)) {
                    w.color = ColorEnum.RED;
                    x = (RedBlackNode)x.parent;
                } else if (w != nilNode) {
                    if (isBlack(w.right)) { // case 3 sibling is black and its left child is red and right child is black
                        ((RedBlackNode)w.left).color = ColorEnum.BLACK;
                        w.color = ColorEnum.RED;
                        rotateRight(w);
                        w = (RedBlackNode)x.parent.right;
                    }
                    w.color = ((RedBlackNode)x.parent).color; // case 4 sibling is black and right child is red
                    ((RedBlackNode)x.parent).color = ColorEnum.BLACK;
                    ((RedBlackNode)w.right).color = ColorEnum.BLACK;
                    rotateLeft(x.parent);
                    x = (RedBlackNode)root;
                } else {
                    x.color = ColorEnum.BLACK;
                    x = (RedBlackNode)x.parent;
                }
            } else {
                RedBlackNode w = (RedBlackNode)x.parent.left;
                if (isRed(w)) { // case 1 - sibling is red
                    w.color = ColorEnum.BLACK;
                    ((RedBlackNode)x.parent).color = ColorEnum.RED;
                    rotateRight(x.parent);
                    w = (RedBlackNode)x.parent.left; // converted to case 2, 3 or 4
                }
                // case 2 sibling is black and both of its children are black
                if (isBlack(w.left) && isBlack(w.right)) {
                    w.color = ColorEnum.RED;
                    x = (RedBlackNode)x.parent;
                } else if (w != nilNode) {
                    if (isBlack(w.left)) { // case 3 sibling is black and its right child is red and left child is black
                        ((RedBlackNode)w.right).color = ColorEnum.BLACK;
                        w.color = ColorEnum.RED;
                        rotateLeft(w);
                        w = (RedBlackNode)x.parent.left;
                    }
                    w.color = ((RedBlackNode)x.parent).color; // case 4 sibling is black and left child is red
                    ((RedBlackNode)x.parent).color = ColorEnum.BLACK;
                    ((RedBlackNode)w.left).color = ColorEnum.BLACK;
                    rotateRight(x.parent);
                    x = (RedBlackNode)root;
                } else {
                    x.color = ColorEnum.BLACK;
                    x = (RedBlackNode)x.parent;
                }
            }

        }
    }

    private boolean isBlack(Node node) {
        return node != null ? ((RedBlackNode)node).color == ColorEnum.BLACK : false;
    }

    private boolean isRed(Node node) {
        return node != null ? ((RedBlackNode)node).color == ColorEnum.RED : false;
    }

    /**
     * Restores Red-Black tree properties after insert if needed. Insert can
     * break only 2 properties: root is red or if node is red then children must
     * be black.
     * 触发插入节点的调整函数
     */
    private void insertRBFixup(RedBlackNode currentNode) {
        // 新插入的节点一定为red，如果x和父节点都为红进行处理，父节点为黑则忽视
        // Red-Black property, otherwise no fixup needed and loop can terminate
        while (currentNode.parent != root && ((RedBlackNode) currentNode.parent).color == ColorEnum.RED) {
            RedBlackNode parent = (RedBlackNode) currentNode.parent;
            RedBlackNode grandParent = (RedBlackNode) parent.parent;
            // 父节点为祖父节点的左孩子时
            if (parent == grandParent.left) {
                RedBlackNode uncle = (RedBlackNode) grandParent.right;
                // case1 - 叔父和父节点都为红，红色节点变为黑节点
                if (((RedBlackNode) uncle).color == ColorEnum.RED) {
                    parent.color = ColorEnum.BLACK;
                    uncle.color = ColorEnum.BLACK;
                    grandParent.color = ColorEnum.RED;
                    // 祖父节点也变成红色，所以下一个检查目标挪到了祖父节点
                    currentNode = grandParent;
                }
                // case 2 叔父节点有一个为黑的话
                else {
                    if (currentNode == parent.right) { // case 2.1, 如果当前节点为右孩子的话，让父节点进行左旋 LR左旋到LL，然后补色进行右旋，共旋转两次
                        currentNode = parent;
                        rotateLeft(currentNode);
                    }
                    // 由于插入节点为红节点，需要把父节点染黑，祖父
                    parent.color = ColorEnum.BLACK; // case 3
                    grandParent.color = ColorEnum.RED;
                    rotateRight(grandParent);
                }
            } else if (parent == grandParent.right) {
                RedBlackNode uncle = (RedBlackNode) grandParent.left;
                // case1 - 叔父和父节点都为红，红色节点变为黑节点
                if (((RedBlackNode) uncle).color == ColorEnum.RED) {
                    parent.color = ColorEnum.BLACK;
                    uncle.color = ColorEnum.BLACK;
                    grandParent.color = ColorEnum.RED;
                    // 祖父节点也变成红色，所以下一个检查目标挪到了祖父节点
                    currentNode = grandParent;
                }
                // case 2 叔父节点有一个为黑的话
                else {
                    if (currentNode == parent.left) { // case 2.1, 如果当前节点为右孩子的话，让父节点进行右旋 RL左旋到RR，然后补色进行左旋，共旋转两次
                        currentNode = parent;
                        rotateRight(currentNode);
                    }
                    // 由于插入节点为红节点，需要把父节点，祖父节点染黑
                    parent.color = ColorEnum.BLACK; // case 3
                    grandParent.color = ColorEnum.RED;
                    rotateLeft(grandParent);
                }
            }

        }
        // ensure root is black in case it was colored red in fixup
        // 确保根节点不会被递归过程染红
        ((RedBlackNode) root).color = ColorEnum.BLACK;
    }

    protected static class RedBlackNode extends Node {
        public ColorEnum color;

        public RedBlackNode(Integer value, Node parent, Node left, Node right, ColorEnum color) {
            super(value, parent, left, right);
            this.color = color;
        }
    }

}
