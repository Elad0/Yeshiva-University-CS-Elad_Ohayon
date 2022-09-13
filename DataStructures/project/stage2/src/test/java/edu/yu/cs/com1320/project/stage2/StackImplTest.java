package edu.yu.cs.com1320.project.stage2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.impl.StackImpl;

class StackImplTest {

	@Test
	void pushandpop() {
		StackImpl stack = new StackImpl();
		stack.push("Gary");
		stack.push(7);
		assert(((Integer)stack.pop()==7));
		assert(stack.pop().equals("Gary"));
	}
	@Test
	void peek() {
		StackImpl stack = new StackImpl();
		assert(stack.peek()==null);
		stack.push("gary");
		assert(stack.peek().equals("gary"));
		assert(stack.peek()==stack.pop());
		stack.push("lk");
		assert(stack.peek()!="gary");
		//assert(stack.pop()!="gary");
		assert(stack.pop().equals("lk"));
		
	}
	
	@Test
	void sizeTest() {
		System.out.println("Size Test");
		StackImpl stack = new StackImpl();
		assert(stack.size()==0);
		stack.push(stack);
		stack.push(stack);
		stack.push(stack);
		assert(stack.size()==3);
		stack.pop();
		assert(stack.size()==2);
		stack.pop();
		stack.pop();
		assert(stack.size()==0);
		stack.pop();
		assert(stack.size()!=-1);
		assert(stack.pop()==null);
		System.out.println("Size Test ends");
		
	}
	

}
