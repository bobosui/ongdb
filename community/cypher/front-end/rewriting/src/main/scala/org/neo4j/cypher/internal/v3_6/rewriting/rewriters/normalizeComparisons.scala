/*
 * Copyright (c) 2018-2020 "Graph Foundation"
 * Graph Foundation, Inc. [https://graphfoundation.org]
 *
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.cypher.internal.v3_6.rewriting.rewriters

import org.neo4j.cypher.internal.v3_6.expressions._
import org.neo4j.cypher.internal.v3_6.util.{Rewriter, topDown}

case object normalizeComparisons extends Rewriter {

  override def apply(that: AnyRef): AnyRef = instance(that)

  private val instance: Rewriter = topDown(Rewriter.lift {
    case c@NotEquals(lhs, rhs) =>
      NotEquals(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@Equals(lhs, rhs) =>
      Equals(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@LessThan(lhs, rhs) =>
      LessThan(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@LessThanOrEqual(lhs, rhs) =>
      LessThanOrEqual(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@GreaterThan(lhs, rhs) =>
      GreaterThan(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@GreaterThanOrEqual(lhs, rhs) =>
      GreaterThanOrEqual(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@InvalidNotEquals(lhs, rhs) =>
      InvalidNotEquals(lhs.endoRewrite(copyVariables), rhs.endoRewrite(copyVariables))(c.position)
    case c@HasLabels(expr, labels) if labels.size > 1 =>
      val hasLabels = labels.map(l => HasLabels(expr.endoRewrite(copyVariables), Seq(l))(c.position))
      Ands(hasLabels.toSet)(c.position)
  })
}
