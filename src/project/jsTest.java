package project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.*;

public class jsTest {
	        public static void main(String[] args) throws Exception{
	                // �����ű����������
	                ScriptEngineManager factory = new ScriptEngineManager();
	                // ����JavaScript����
	                ScriptEngine engine = factory.getEngineByName("JavaScript");
	                // ���ַ����и�ֵJavaScript�ű�
	                engine.eval(new java.io.FileReader("src/myjs.js"));
	        }
}
