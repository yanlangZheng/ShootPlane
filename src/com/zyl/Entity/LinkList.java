package com.zyl.Entity;
/**
 * 链表设计
 * @author zyl
 *
 */
public class LinkList {
	Nodes head=null;
	/**
	 * 向链表中按从大到小插入
	 * @param name,score
	 *
	 */
	public void addNode(String name,String score) {
		Nodes newNode=new Nodes(name,score);//实例化节点
		if(head==null) {
			head=newNode;
			return;
		}
		Nodes temp=head;
		Nodes pre=head;
		while(temp!=null) {
			if(charToInt(temp.score)<charToInt(newNode.score)) {
				//newNode比链表头结点还大
				if(temp==head) {
					newNode.next=temp;
					head=newNode;
					return;
				}
				else {
					newNode.next=temp;
					pre.next=newNode;
					return;
				}
			}
			pre=temp;
			temp=temp.next;
		}
	}	
	/**
	 * 返回长度
	 */
	public int length() {
		int length=0;
		Nodes temp=head;
		while(temp!=null) {
			length++;
			temp=temp.next;
		}
		return length;
	}
	/**
	 * 输出
	 */
	public void output() {
		Nodes temp=head;
		while(temp!=null) {
			System.out.println(temp.name+" "+temp.score);
			temp=temp.next;
		}
	}
	/**
	 * 获得第i个节点数据
	 */
	public String getName(int i) {
		int length=0;
		Nodes temp=head;
		while(temp!=null) {
			length++;
			if(length==i) {
				return temp.name;
			}	
			temp=temp.next;
		}
		return null;
	}
	public String getScore(int i) {
		int length=0;
		Nodes temp=head;
		while(temp!=null) {
			length++;
			if(length==i) {
				return temp.score;
			}
			
			temp=temp.next;
		}
		return null;
	}
	class Nodes{
		Nodes next=null;
		String name,score;
		public Nodes(String name,String score) {
			this.name=name;
			this.score=score;
		}
	}
	public int charToInt(String h) {
		int len=Integer.parseInt(h);
		return len;
	}
}
