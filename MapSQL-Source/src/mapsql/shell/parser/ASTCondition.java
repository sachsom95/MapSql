/* Generated By:JJTree: Do not edit this line. ASTCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package mapsql.shell.parser;

public
class ASTCondition extends SimpleNode {
  public ASTCondition(int id) {
    super(id);
  }

  public ASTCondition(MapSQL p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MapSQLVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e4c1b442767898d231864e18f08707a4 (do not edit this line) */
