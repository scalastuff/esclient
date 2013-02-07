package org.scalastuff

import scala.concurrent.Future
import org.elasticsearch.client.Client

package object esclient {
  implicit class ESClient(val javaClient: Client) {
    def apply[Request, Response](request: Request)(implicit action: ActionMagnet[Request, Response]): Future[Response] =
      action.execute(javaClient, request)
  }
}


