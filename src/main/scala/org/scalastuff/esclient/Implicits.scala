package org.scalastuff.esclient

import org.elasticsearch.client.Client

package object implicits {
  implicit def esclient(javaClient : Client) = 
    new ESClient(javaClient)
}