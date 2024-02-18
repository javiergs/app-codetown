package com.javiergs.codetown.model;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.javiergs.codetown.Main;
import com.javiergs.codetown.data.JgsFileData;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;

/**
 * This class is responsible for parsing a Java file and extracting the relevant information
 * It uses the JavaParser library to parse the file and extract the information
 * @version 1.0
 * @autor javierGS
 */
public class JgsFileParser {

  public static JgsFileData parseJavaFile(File file)  {

    JgsFileData jgsFileData = new JgsFileData();
    CompilationUnit compilationUnit = null;
    try {
      compilationUnit = StaticJavaParser.parse(file);
    } catch (Exception e) {
      LoggerFactory.getLogger(Main.class).error("error parsing file " + e);
    }
    Optional<ClassOrInterfaceDeclaration> classDeclaration = compilationUnit
      .findAll(ClassOrInterfaceDeclaration.class)
      .stream()
      .findFirst();

    if (!classDeclaration.isPresent())
      return null;

    //name of the class
    jgsFileData.setName(classDeclaration.get().getNameAsString());

    //parent classes
    if (classDeclaration.get().getExtendedTypes().size() > 0) {
      jgsFileData.setParent(classDeclaration.get().getExtendedTypes().get(0).toString());
    }

    //interfaces
    Iterator<ClassOrInterfaceType> loop = classDeclaration.get().getImplementedTypes().iterator();
    while (loop.hasNext()) {
      jgsFileData.addInterface(loop.next().toString());
    }

    //attributes
    Iterator<FieldDeclaration> loop2 = classDeclaration.get().getFields().iterator();
    while (loop2.hasNext()) {
      jgsFileData.addAttribute(loop2.next().getElementType().toString());
    }

    //methods
    Iterator<MethodDeclaration> loop3 = classDeclaration.get().getMethods().iterator();
    while (loop3.hasNext()) {
      jgsFileData.addMethod(loop3.next().getNameAsString());
    }

    // local associations
    classDeclaration.get().getMethods().forEach(method -> {
      method.findAll(ClassOrInterfaceType.class).forEach(type -> {
        jgsFileData.addAssociation(type.getNameAsString());
      });
      method.findAll(MethodCallExpr.class).forEach(mce -> {
        if (mce.getScope().isPresent() && Character.isUpperCase(mce.getScope().get().toString().charAt(0))) {
            jgsFileData.addAssociation(mce.getScope().get().toString());
        }
      });
    });

    // lines of code
    jgsFileData.setLinesOfCode(compilationUnit.toString().split("\n").length);
    System.out.println(jgsFileData);
    return jgsFileData;
  }

}
