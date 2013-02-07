package org.scalastuff

import scala.concurrent.Future
import org.elasticsearch.client.Client
import org.elasticsearch.action.ActionRequest
import org.elasticsearch.action.ActionResponse

package object esclient {
  implicit class ESClient(val javaClient: Client) extends AnyVal {
    def execute[Request, Response](request: Request)(implicit action: ActionMagnet[Request, Response]): Future[Response] =
      action.execute(javaClient, request)
  }
}


