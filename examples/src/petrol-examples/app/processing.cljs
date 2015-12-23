(ns petrol-examples.app.processing
  (:require [petrol.core :refer [Message process-message EventSource watch-channels
                                 process-submessage watch-subchannels]]
            [petrol-examples.app.messages :as m]))
