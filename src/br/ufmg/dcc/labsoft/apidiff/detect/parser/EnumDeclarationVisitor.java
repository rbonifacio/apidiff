package br.ufmg.dcc.labsoft.apidiff.detect.parser;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;

import br.ufmg.dcc.labsoft.apidiff.UtilTools;

public class EnumDeclarationVisitor extends ASTVisitor{
	private ArrayList<EnumDeclaration> acessibleEnums = new ArrayList<EnumDeclaration>();
	private ArrayList<EnumDeclaration> nonAcessibleEnums = new ArrayList<EnumDeclaration>();

	public ArrayList<EnumDeclaration> getAcessibleEnums() {
		return acessibleEnums;
	}

	public ArrayList<EnumDeclaration> getNonAcessibleEnums() {
		return nonAcessibleEnums;
	}

	@Override
	public boolean visit(EnumDeclaration node){
		if(UtilTools.isInterfaceStable(node)){
			if(UtilTools.isPrivate(node)){
				this.nonAcessibleEnums.add(node);
			} else {
				this.acessibleEnums.add(node);
			}
		}
		return super.visit(node);
	}

}