package project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.*;

public class jsTest {
	        public static void main(String[] args) throws Exception{
	                // 创建脚本引擎管理器
	                ScriptEngineManager factory = new ScriptEngineManager();
	                // 创建JavaScript引擎
	                ScriptEngine engine = factory.getEngineByName("JavaScript");
	                // 从字符串中赋值JavaScript脚本
	                engine.eval(new java.io.FileReader("src/myjs.js"));
	        }
}
