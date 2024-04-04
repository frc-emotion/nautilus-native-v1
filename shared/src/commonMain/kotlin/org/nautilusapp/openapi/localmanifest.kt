package org.nautilusapp.openapi

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
}

val MANIFEST = json.decodeFromString<SwaggerManifest>(
    """
    {
      "openapi": "3.0.3",
      "info": {
        "title": "Nautilus API",
        "description": "Backend API for Nautilus FRC",
        "version": "0.1.0-beta"
      },
      "tags": [
        {
          "name": "Users",
          "description": "User account related endpoints"
        },
        {
          "name": "Attendance",
          "description": "Endpoints related to logging and verifying user attendance via the companion app"
        },
        {
          "name": "Roles",
          "description": "Endpoints related to user roles and permissions"
        },
        {
          "name": "Seasons",
          "description": "Endpoints for information related to FRC seasons and competitions"
        },
        {
          "name": "Scouting",
          "description": "Endpoints for scouting"
        },
        {
          "name": "Crescendo",
          "description": "Endpoints for the 2024 FRC game, Crescendo"
        },
        {
          "name": "Inpit",
          "description": "Endpoints for the inpit scouting system"
        }
      ],
      "paths": {
        "/users/": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "anyOf": [
                  {
                    "type": "array",
                    "items": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "phone": {
                          "type": "string"
                        },
                        "grade": {
                          "type": "number"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "phone",
                        "roles",
                        "accountType"
                      ]
                    }
                  },
                  {
                    "type": "array",
                    "items": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ]
                    }
                  }
                ],
                "content": {
                  "application/json": {
                    "schema": {
                      "anyOf": [
                        {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": {
                              "_id": {
                                "type": "string"
                              },
                              "firstname": {
                                "type": "string"
                              },
                              "lastname": {
                                "type": "string"
                              },
                              "username": {
                                "type": "string"
                              },
                              "email": {
                                "type": "string"
                              },
                              "phone": {
                                "type": "string"
                              },
                              "grade": {
                                "type": "number"
                              },
                              "subteam": {
                                "type": "string"
                              },
                              "roles": {
                                "type": "array",
                                "items": {
                                  "type": "string"
                                }
                              },
                              "accountType": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "_id",
                              "firstname",
                              "lastname",
                              "username",
                              "email",
                              "phone",
                              "roles",
                              "accountType"
                            ]
                          }
                        },
                        {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": {
                              "_id": {
                                "type": "string"
                              },
                              "firstname": {
                                "type": "string"
                              },
                              "lastname": {
                                "type": "string"
                              },
                              "username": {
                                "type": "string"
                              },
                              "email": {
                                "type": "string"
                              },
                              "subteam": {
                                "type": "string"
                              },
                              "roles": {
                                "type": "array",
                                "items": {
                                  "type": "string"
                                }
                              },
                              "accountType": {
                                "type": "number"
                              },
                              "accountUpdateVersion": {
                                "type": "number"
                              },
                              "attendance": {
                                "type": "object",
                                "patternProperties": {
                                  "^(.*)${'$'}": {
                                    "type": "object",
                                    "properties": {
                                      "totalHoursLogged": {
                                        "anyOf": [
                                          {
                                            "format": "numeric",
                                            "default": 0,
                                            "type": "string"
                                          },
                                          {
                                            "type": "number"
                                          }
                                        ]
                                      },
                                      "logs": {
                                        "type": "array",
                                        "items": {
                                          "type": "object",
                                          "properties": {
                                            "meetingId": {
                                              "type": "string"
                                            },
                                            "verifiedBy": {
                                              "type": "string"
                                            }
                                          },
                                          "required": [
                                            "meetingId",
                                            "verifiedBy"
                                          ]
                                        }
                                      }
                                    },
                                    "required": [
                                      "totalHoursLogged",
                                      "logs"
                                    ]
                                  }
                                }
                              },
                              "grade": {
                                "anyOf": [
                                  {
                                    "format": "numeric",
                                    "default": 0,
                                    "type": "string"
                                  },
                                  {
                                    "type": "number"
                                  }
                                ]
                              },
                              "permissions": {
                                "type": "object",
                                "properties": {
                                  "generalScouting": {
                                    "type": "boolean"
                                  },
                                  "pitScouting": {
                                    "type": "boolean"
                                  },
                                  "viewMeetings": {
                                    "type": "boolean"
                                  },
                                  "viewScoutingData": {
                                    "type": "boolean"
                                  },
                                  "blogPosts": {
                                    "type": "boolean"
                                  },
                                  "deleteMeetings": {
                                    "type": "boolean"
                                  },
                                  "makeAnnouncements": {
                                    "type": "boolean"
                                  },
                                  "makeMeetings": {
                                    "type": "boolean"
                                  }
                                },
                                "required": [
                                  "generalScouting",
                                  "pitScouting",
                                  "viewMeetings",
                                  "viewScoutingData",
                                  "blogPosts",
                                  "deleteMeetings",
                                  "makeAnnouncements",
                                  "makeMeetings"
                                ]
                              },
                              "phone": {
                                "type": "string"
                              }
                            },
                            "required": [
                              "_id",
                              "firstname",
                              "lastname",
                              "username",
                              "email",
                              "roles",
                              "accountType",
                              "accountUpdateVersion",
                              "attendance",
                              "permissions",
                              "phone"
                            ]
                          }
                        }
                      ]
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "anyOf": [
                        {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": {
                              "_id": {
                                "type": "string"
                              },
                              "firstname": {
                                "type": "string"
                              },
                              "lastname": {
                                "type": "string"
                              },
                              "username": {
                                "type": "string"
                              },
                              "email": {
                                "type": "string"
                              },
                              "phone": {
                                "type": "string"
                              },
                              "grade": {
                                "type": "number"
                              },
                              "subteam": {
                                "type": "string"
                              },
                              "roles": {
                                "type": "array",
                                "items": {
                                  "type": "string"
                                }
                              },
                              "accountType": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "_id",
                              "firstname",
                              "lastname",
                              "username",
                              "email",
                              "phone",
                              "roles",
                              "accountType"
                            ]
                          }
                        },
                        {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": {
                              "_id": {
                                "type": "string"
                              },
                              "firstname": {
                                "type": "string"
                              },
                              "lastname": {
                                "type": "string"
                              },
                              "username": {
                                "type": "string"
                              },
                              "email": {
                                "type": "string"
                              },
                              "subteam": {
                                "type": "string"
                              },
                              "roles": {
                                "type": "array",
                                "items": {
                                  "type": "string"
                                }
                              },
                              "accountType": {
                                "type": "number"
                              },
                              "accountUpdateVersion": {
                                "type": "number"
                              },
                              "attendance": {
                                "type": "object",
                                "patternProperties": {
                                  "^(.*)${'$'}": {
                                    "type": "object",
                                    "properties": {
                                      "totalHoursLogged": {
                                        "anyOf": [
                                          {
                                            "format": "numeric",
                                            "default": 0,
                                            "type": "string"
                                          },
                                          {
                                            "type": "number"
                                          }
                                        ]
                                      },
                                      "logs": {
                                        "type": "array",
                                        "items": {
                                          "type": "object",
                                          "properties": {
                                            "meetingId": {
                                              "type": "string"
                                            },
                                            "verifiedBy": {
                                              "type": "string"
                                            }
                                          },
                                          "required": [
                                            "meetingId",
                                            "verifiedBy"
                                          ]
                                        }
                                      }
                                    },
                                    "required": [
                                      "totalHoursLogged",
                                      "logs"
                                    ]
                                  }
                                }
                              },
                              "grade": {
                                "anyOf": [
                                  {
                                    "format": "numeric",
                                    "default": 0,
                                    "type": "string"
                                  },
                                  {
                                    "type": "number"
                                  }
                                ]
                              },
                              "permissions": {
                                "type": "object",
                                "properties": {
                                  "generalScouting": {
                                    "type": "boolean"
                                  },
                                  "pitScouting": {
                                    "type": "boolean"
                                  },
                                  "viewMeetings": {
                                    "type": "boolean"
                                  },
                                  "viewScoutingData": {
                                    "type": "boolean"
                                  },
                                  "blogPosts": {
                                    "type": "boolean"
                                  },
                                  "deleteMeetings": {
                                    "type": "boolean"
                                  },
                                  "makeAnnouncements": {
                                    "type": "boolean"
                                  },
                                  "makeMeetings": {
                                    "type": "boolean"
                                  }
                                },
                                "required": [
                                  "generalScouting",
                                  "pitScouting",
                                  "viewMeetings",
                                  "viewScoutingData",
                                  "blogPosts",
                                  "deleteMeetings",
                                  "makeAnnouncements",
                                  "makeMeetings"
                                ]
                              },
                              "phone": {
                                "type": "string"
                              }
                            },
                            "required": [
                              "_id",
                              "firstname",
                              "lastname",
                              "username",
                              "email",
                              "roles",
                              "accountType",
                              "accountUpdateVersion",
                              "attendance",
                              "permissions",
                              "phone"
                            ]
                          }
                        }
                      ]
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "anyOf": [
                        {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": {
                              "_id": {
                                "type": "string"
                              },
                              "firstname": {
                                "type": "string"
                              },
                              "lastname": {
                                "type": "string"
                              },
                              "username": {
                                "type": "string"
                              },
                              "email": {
                                "type": "string"
                              },
                              "phone": {
                                "type": "string"
                              },
                              "grade": {
                                "type": "number"
                              },
                              "subteam": {
                                "type": "string"
                              },
                              "roles": {
                                "type": "array",
                                "items": {
                                  "type": "string"
                                }
                              },
                              "accountType": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "_id",
                              "firstname",
                              "lastname",
                              "username",
                              "email",
                              "phone",
                              "roles",
                              "accountType"
                            ]
                          }
                        },
                        {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": {
                              "_id": {
                                "type": "string"
                              },
                              "firstname": {
                                "type": "string"
                              },
                              "lastname": {
                                "type": "string"
                              },
                              "username": {
                                "type": "string"
                              },
                              "email": {
                                "type": "string"
                              },
                              "subteam": {
                                "type": "string"
                              },
                              "roles": {
                                "type": "array",
                                "items": {
                                  "type": "string"
                                }
                              },
                              "accountType": {
                                "type": "number"
                              },
                              "accountUpdateVersion": {
                                "type": "number"
                              },
                              "attendance": {
                                "type": "object",
                                "patternProperties": {
                                  "^(.*)${'$'}": {
                                    "type": "object",
                                    "properties": {
                                      "totalHoursLogged": {
                                        "anyOf": [
                                          {
                                            "format": "numeric",
                                            "default": 0,
                                            "type": "string"
                                          },
                                          {
                                            "type": "number"
                                          }
                                        ]
                                      },
                                      "logs": {
                                        "type": "array",
                                        "items": {
                                          "type": "object",
                                          "properties": {
                                            "meetingId": {
                                              "type": "string"
                                            },
                                            "verifiedBy": {
                                              "type": "string"
                                            }
                                          },
                                          "required": [
                                            "meetingId",
                                            "verifiedBy"
                                          ]
                                        }
                                      }
                                    },
                                    "required": [
                                      "totalHoursLogged",
                                      "logs"
                                    ]
                                  }
                                }
                              },
                              "grade": {
                                "anyOf": [
                                  {
                                    "format": "numeric",
                                    "default": 0,
                                    "type": "string"
                                  },
                                  {
                                    "type": "number"
                                  }
                                ]
                              },
                              "permissions": {
                                "type": "object",
                                "properties": {
                                  "generalScouting": {
                                    "type": "boolean"
                                  },
                                  "pitScouting": {
                                    "type": "boolean"
                                  },
                                  "viewMeetings": {
                                    "type": "boolean"
                                  },
                                  "viewScoutingData": {
                                    "type": "boolean"
                                  },
                                  "blogPosts": {
                                    "type": "boolean"
                                  },
                                  "deleteMeetings": {
                                    "type": "boolean"
                                  },
                                  "makeAnnouncements": {
                                    "type": "boolean"
                                  },
                                  "makeMeetings": {
                                    "type": "boolean"
                                  }
                                },
                                "required": [
                                  "generalScouting",
                                  "pitScouting",
                                  "viewMeetings",
                                  "viewScoutingData",
                                  "blogPosts",
                                  "deleteMeetings",
                                  "makeAnnouncements",
                                  "makeMeetings"
                                ]
                              },
                              "phone": {
                                "type": "string"
                              }
                            },
                            "required": [
                              "_id",
                              "firstname",
                              "lastname",
                              "username",
                              "email",
                              "roles",
                              "accountType",
                              "accountUpdateVersion",
                              "attendance",
                              "permissions",
                              "phone"
                            ]
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getUsers",
            "description": "Returns a list of all users. Access level needed: Admin (account level 3+) to view all user data, base (level 1+) to view limited user data",
            "tags": [
              "Users"
            ]
          }
        },
        "/users/raw": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "_id": {

                    },
                    "firstname": {
                      "type": "string"
                    },
                    "lastname": {
                      "type": "string"
                    },
                    "username": {
                      "type": "string"
                    },
                    "email": {
                      "type": "string"
                    },
                    "password": {
                      "type": "string"
                    },
                    "subteam": {
                      "type": "string"
                    },
                    "roles": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "name": {
                            "type": "string"
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "id": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "name",
                          "permissions",
                          "id"
                        ]
                      }
                    },
                    "accountType": {
                      "type": "number"
                    },
                    "accountUpdateVersion": {
                      "type": "number"
                    },
                    "attendance": {
                      "type": "object",
                      "patternProperties": {
                        "^(.*)${'$'}": {
                          "type": "object",
                          "properties": {
                            "totalHoursLogged": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "logs": {
                              "type": "array",
                              "items": {
                                "type": "object",
                                "properties": {
                                  "meetingId": {
                                    "type": "string"
                                  },
                                  "verifiedBy": {
                                    "type": "string"
                                  }
                                },
                                "required": [
                                  "meetingId",
                                  "verifiedBy"
                                ]
                              }
                            }
                          },
                          "required": [
                            "totalHoursLogged",
                            "logs"
                          ]
                        }
                      }
                    },
                    "grade": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "phone": {
                      "type": "string"
                    },
                    "rateLimit": {
                      "type": "object",
                      "properties": {
                        "count": {
                          "type": "number"
                        },
                        "expiresAt": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "count",
                        "expiresAt"
                      ]
                    },
                    "forgotPassword": {
                      "type": "object",
                      "properties": {
                        "code": {
                          "type": "string"
                        },
                        "expiresAt": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "code",
                        "expiresAt"
                      ]
                    }
                  },
                  "required": [
                    "_id",
                    "firstname",
                    "lastname",
                    "username",
                    "email",
                    "password",
                    "roles",
                    "accountType",
                    "accountUpdateVersion",
                    "attendance",
                    "phone"
                  ]
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {

                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "password": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "object",
                              "properties": {
                                "name": {
                                  "type": "string"
                                },
                                "permissions": {
                                  "type": "object",
                                  "properties": {
                                    "generalScouting": {
                                      "type": "boolean"
                                    },
                                    "pitScouting": {
                                      "type": "boolean"
                                    },
                                    "viewMeetings": {
                                      "type": "boolean"
                                    },
                                    "viewScoutingData": {
                                      "type": "boolean"
                                    },
                                    "blogPosts": {
                                      "type": "boolean"
                                    },
                                    "deleteMeetings": {
                                      "type": "boolean"
                                    },
                                    "makeAnnouncements": {
                                      "type": "boolean"
                                    },
                                    "makeMeetings": {
                                      "type": "boolean"
                                    }
                                  },
                                  "required": [
                                    "generalScouting",
                                    "pitScouting",
                                    "viewMeetings",
                                    "viewScoutingData",
                                    "blogPosts",
                                    "deleteMeetings",
                                    "makeAnnouncements",
                                    "makeMeetings"
                                  ]
                                },
                                "id": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "name",
                                "permissions",
                                "id"
                              ]
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "phone": {
                            "type": "string"
                          },
                          "rateLimit": {
                            "type": "object",
                            "properties": {
                              "count": {
                                "type": "number"
                              },
                              "expiresAt": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "count",
                              "expiresAt"
                            ]
                          },
                          "forgotPassword": {
                            "type": "object",
                            "properties": {
                              "code": {
                                "type": "string"
                              },
                              "expiresAt": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "code",
                              "expiresAt"
                            ]
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "password",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "phone"
                        ]
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {

                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "password": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "object",
                              "properties": {
                                "name": {
                                  "type": "string"
                                },
                                "permissions": {
                                  "type": "object",
                                  "properties": {
                                    "generalScouting": {
                                      "type": "boolean"
                                    },
                                    "pitScouting": {
                                      "type": "boolean"
                                    },
                                    "viewMeetings": {
                                      "type": "boolean"
                                    },
                                    "viewScoutingData": {
                                      "type": "boolean"
                                    },
                                    "blogPosts": {
                                      "type": "boolean"
                                    },
                                    "deleteMeetings": {
                                      "type": "boolean"
                                    },
                                    "makeAnnouncements": {
                                      "type": "boolean"
                                    },
                                    "makeMeetings": {
                                      "type": "boolean"
                                    }
                                  },
                                  "required": [
                                    "generalScouting",
                                    "pitScouting",
                                    "viewMeetings",
                                    "viewScoutingData",
                                    "blogPosts",
                                    "deleteMeetings",
                                    "makeAnnouncements",
                                    "makeMeetings"
                                  ]
                                },
                                "id": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "name",
                                "permissions",
                                "id"
                              ]
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "phone": {
                            "type": "string"
                          },
                          "rateLimit": {
                            "type": "object",
                            "properties": {
                              "count": {
                                "type": "number"
                              },
                              "expiresAt": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "count",
                              "expiresAt"
                            ]
                          },
                          "forgotPassword": {
                            "type": "object",
                            "properties": {
                              "code": {
                                "type": "string"
                              },
                              "expiresAt": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "code",
                              "expiresAt"
                            ]
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "password",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "phone"
                        ]
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {

                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "password": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "object",
                              "properties": {
                                "name": {
                                  "type": "string"
                                },
                                "permissions": {
                                  "type": "object",
                                  "properties": {
                                    "generalScouting": {
                                      "type": "boolean"
                                    },
                                    "pitScouting": {
                                      "type": "boolean"
                                    },
                                    "viewMeetings": {
                                      "type": "boolean"
                                    },
                                    "viewScoutingData": {
                                      "type": "boolean"
                                    },
                                    "blogPosts": {
                                      "type": "boolean"
                                    },
                                    "deleteMeetings": {
                                      "type": "boolean"
                                    },
                                    "makeAnnouncements": {
                                      "type": "boolean"
                                    },
                                    "makeMeetings": {
                                      "type": "boolean"
                                    }
                                  },
                                  "required": [
                                    "generalScouting",
                                    "pitScouting",
                                    "viewMeetings",
                                    "viewScoutingData",
                                    "blogPosts",
                                    "deleteMeetings",
                                    "makeAnnouncements",
                                    "makeMeetings"
                                  ]
                                },
                                "id": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "name",
                                "permissions",
                                "id"
                              ]
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "phone": {
                            "type": "string"
                          },
                          "rateLimit": {
                            "type": "object",
                            "properties": {
                              "count": {
                                "type": "number"
                              },
                              "expiresAt": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "count",
                              "expiresAt"
                            ]
                          },
                          "forgotPassword": {
                            "type": "object",
                            "properties": {
                              "code": {
                                "type": "string"
                              },
                              "expiresAt": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "code",
                              "expiresAt"
                            ]
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "password",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "phone"
                        ]
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getUsersRaw",
            "description": "Returns a list of users as their raw MongoDB documents. Superuser access needed (account level 4+)",
            "tags": [
              "Users"
            ]
          }
        },
        "/users/login": {
          "post": {
            "parameters": [],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postUsersLogin",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "username": {
                        "type": "string"
                      },
                      "password": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "username",
                      "password"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "username": {
                        "type": "string"
                      },
                      "password": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "username",
                      "password"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "username": {
                        "type": "string"
                      },
                      "password": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "username",
                      "password"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/users/register": {
          "post": {
            "parameters": [],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postUsersRegister",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "username": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "password": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      }
                    },
                    "required": [
                      "firstname",
                      "lastname",
                      "username",
                      "email",
                      "password",
                      "phone",
                      "subteam",
                      "grade"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "username": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "password": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      }
                    },
                    "required": [
                      "firstname",
                      "lastname",
                      "username",
                      "email",
                      "password",
                      "phone",
                      "subteam",
                      "grade"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "username": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "password": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      }
                    },
                    "required": [
                      "firstname",
                      "lastname",
                      "username",
                      "email",
                      "password",
                      "phone",
                      "subteam",
                      "grade"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/users/forgot-password": {
          "post": {
            "parameters": [],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "429": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postUsersForgot-password",
            "description": "If the email is associated with an existing user account, sends an email with a one-time password to reset the password. Users are limited to 5 password reset requests per week",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "email": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "email"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "email": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "email"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "email": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "email"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/users/reset-password": {
          "post": {
            "parameters": [],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postUsersReset-password",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "email": {
                        "type": "string"
                      },
                      "newPassword": {
                        "type": "string"
                      },
                      "otp": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "email",
                      "newPassword",
                      "otp"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "email": {
                        "type": "string"
                      },
                      "newPassword": {
                        "type": "string"
                      },
                      "otp": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "email",
                      "newPassword",
                      "otp"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "email": {
                        "type": "string"
                      },
                      "newPassword": {
                        "type": "string"
                      },
                      "otp": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "email",
                      "newPassword",
                      "otp"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/users/me": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getUsersMe",
            "description": "Returns the currently authenticated user's data",
            "tags": [
              "Users"
            ]
          },
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putUsersMe",
            "description": "Updates the currently authenticated user's information",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      }
                    },
                    "additionalProperties": false
                  }
                }
              }
            }
          },
          "delete": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "deleteUsersMe",
            "description": "Deletes the currently authenticated user's account",
            "tags": [
              "Users"
            ]
          }
        },
        "/users/{user}": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "anyOf": [
                  {
                    "type": "object",
                    "properties": {
                      "_id": {
                        "type": "string"
                      },
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "username": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "grade": {
                        "type": "number"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "roles": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "accountType": {
                        "type": "number"
                      }
                    },
                    "required": [
                      "_id",
                      "firstname",
                      "lastname",
                      "username",
                      "email",
                      "phone",
                      "roles",
                      "accountType"
                    ]
                  },
                  {
                    "type": "object",
                    "properties": {
                      "_id": {
                        "type": "string"
                      },
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "username": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "roles": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "accountType": {
                        "type": "number"
                      },
                      "accountUpdateVersion": {
                        "type": "number"
                      },
                      "attendance": {
                        "type": "object",
                        "patternProperties": {
                          "^(.*)${'$'}": {
                            "type": "object",
                            "properties": {
                              "totalHoursLogged": {
                                "anyOf": [
                                  {
                                    "format": "numeric",
                                    "default": 0,
                                    "type": "string"
                                  },
                                  {
                                    "type": "number"
                                  }
                                ]
                              },
                              "logs": {
                                "type": "array",
                                "items": {
                                  "type": "object",
                                  "properties": {
                                    "meetingId": {
                                      "type": "string"
                                    },
                                    "verifiedBy": {
                                      "type": "string"
                                    }
                                  },
                                  "required": [
                                    "meetingId",
                                    "verifiedBy"
                                  ]
                                }
                              }
                            },
                            "required": [
                              "totalHoursLogged",
                              "logs"
                            ]
                          }
                        }
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "generalScouting",
                          "pitScouting",
                          "viewMeetings",
                          "viewScoutingData",
                          "blogPosts",
                          "deleteMeetings",
                          "makeAnnouncements",
                          "makeMeetings"
                        ]
                      },
                      "phone": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "_id",
                      "firstname",
                      "lastname",
                      "username",
                      "email",
                      "roles",
                      "accountType",
                      "accountUpdateVersion",
                      "attendance",
                      "permissions",
                      "phone"
                    ]
                  }
                ],
                "content": {
                  "application/json": {
                    "schema": {
                      "anyOf": [
                        {
                          "type": "object",
                          "properties": {
                            "_id": {
                              "type": "string"
                            },
                            "firstname": {
                              "type": "string"
                            },
                            "lastname": {
                              "type": "string"
                            },
                            "username": {
                              "type": "string"
                            },
                            "email": {
                              "type": "string"
                            },
                            "phone": {
                              "type": "string"
                            },
                            "grade": {
                              "type": "number"
                            },
                            "subteam": {
                              "type": "string"
                            },
                            "roles": {
                              "type": "array",
                              "items": {
                                "type": "string"
                              }
                            },
                            "accountType": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "_id",
                            "firstname",
                            "lastname",
                            "username",
                            "email",
                            "phone",
                            "roles",
                            "accountType"
                          ]
                        },
                        {
                          "type": "object",
                          "properties": {
                            "_id": {
                              "type": "string"
                            },
                            "firstname": {
                              "type": "string"
                            },
                            "lastname": {
                              "type": "string"
                            },
                            "username": {
                              "type": "string"
                            },
                            "email": {
                              "type": "string"
                            },
                            "subteam": {
                              "type": "string"
                            },
                            "roles": {
                              "type": "array",
                              "items": {
                                "type": "string"
                              }
                            },
                            "accountType": {
                              "type": "number"
                            },
                            "accountUpdateVersion": {
                              "type": "number"
                            },
                            "attendance": {
                              "type": "object",
                              "patternProperties": {
                                "^(.*)${'$'}": {
                                  "type": "object",
                                  "properties": {
                                    "totalHoursLogged": {
                                      "anyOf": [
                                        {
                                          "format": "numeric",
                                          "default": 0,
                                          "type": "string"
                                        },
                                        {
                                          "type": "number"
                                        }
                                      ]
                                    },
                                    "logs": {
                                      "type": "array",
                                      "items": {
                                        "type": "object",
                                        "properties": {
                                          "meetingId": {
                                            "type": "string"
                                          },
                                          "verifiedBy": {
                                            "type": "string"
                                          }
                                        },
                                        "required": [
                                          "meetingId",
                                          "verifiedBy"
                                        ]
                                      }
                                    }
                                  },
                                  "required": [
                                    "totalHoursLogged",
                                    "logs"
                                  ]
                                }
                              }
                            },
                            "grade": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "permissions": {
                              "type": "object",
                              "properties": {
                                "generalScouting": {
                                  "type": "boolean"
                                },
                                "pitScouting": {
                                  "type": "boolean"
                                },
                                "viewMeetings": {
                                  "type": "boolean"
                                },
                                "viewScoutingData": {
                                  "type": "boolean"
                                },
                                "blogPosts": {
                                  "type": "boolean"
                                },
                                "deleteMeetings": {
                                  "type": "boolean"
                                },
                                "makeAnnouncements": {
                                  "type": "boolean"
                                },
                                "makeMeetings": {
                                  "type": "boolean"
                                }
                              },
                              "required": [
                                "generalScouting",
                                "pitScouting",
                                "viewMeetings",
                                "viewScoutingData",
                                "blogPosts",
                                "deleteMeetings",
                                "makeAnnouncements",
                                "makeMeetings"
                              ]
                            },
                            "phone": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "_id",
                            "firstname",
                            "lastname",
                            "username",
                            "email",
                            "roles",
                            "accountType",
                            "accountUpdateVersion",
                            "attendance",
                            "permissions",
                            "phone"
                          ]
                        }
                      ]
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "anyOf": [
                        {
                          "type": "object",
                          "properties": {
                            "_id": {
                              "type": "string"
                            },
                            "firstname": {
                              "type": "string"
                            },
                            "lastname": {
                              "type": "string"
                            },
                            "username": {
                              "type": "string"
                            },
                            "email": {
                              "type": "string"
                            },
                            "phone": {
                              "type": "string"
                            },
                            "grade": {
                              "type": "number"
                            },
                            "subteam": {
                              "type": "string"
                            },
                            "roles": {
                              "type": "array",
                              "items": {
                                "type": "string"
                              }
                            },
                            "accountType": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "_id",
                            "firstname",
                            "lastname",
                            "username",
                            "email",
                            "phone",
                            "roles",
                            "accountType"
                          ]
                        },
                        {
                          "type": "object",
                          "properties": {
                            "_id": {
                              "type": "string"
                            },
                            "firstname": {
                              "type": "string"
                            },
                            "lastname": {
                              "type": "string"
                            },
                            "username": {
                              "type": "string"
                            },
                            "email": {
                              "type": "string"
                            },
                            "subteam": {
                              "type": "string"
                            },
                            "roles": {
                              "type": "array",
                              "items": {
                                "type": "string"
                              }
                            },
                            "accountType": {
                              "type": "number"
                            },
                            "accountUpdateVersion": {
                              "type": "number"
                            },
                            "attendance": {
                              "type": "object",
                              "patternProperties": {
                                "^(.*)${'$'}": {
                                  "type": "object",
                                  "properties": {
                                    "totalHoursLogged": {
                                      "anyOf": [
                                        {
                                          "format": "numeric",
                                          "default": 0,
                                          "type": "string"
                                        },
                                        {
                                          "type": "number"
                                        }
                                      ]
                                    },
                                    "logs": {
                                      "type": "array",
                                      "items": {
                                        "type": "object",
                                        "properties": {
                                          "meetingId": {
                                            "type": "string"
                                          },
                                          "verifiedBy": {
                                            "type": "string"
                                          }
                                        },
                                        "required": [
                                          "meetingId",
                                          "verifiedBy"
                                        ]
                                      }
                                    }
                                  },
                                  "required": [
                                    "totalHoursLogged",
                                    "logs"
                                  ]
                                }
                              }
                            },
                            "grade": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "permissions": {
                              "type": "object",
                              "properties": {
                                "generalScouting": {
                                  "type": "boolean"
                                },
                                "pitScouting": {
                                  "type": "boolean"
                                },
                                "viewMeetings": {
                                  "type": "boolean"
                                },
                                "viewScoutingData": {
                                  "type": "boolean"
                                },
                                "blogPosts": {
                                  "type": "boolean"
                                },
                                "deleteMeetings": {
                                  "type": "boolean"
                                },
                                "makeAnnouncements": {
                                  "type": "boolean"
                                },
                                "makeMeetings": {
                                  "type": "boolean"
                                }
                              },
                              "required": [
                                "generalScouting",
                                "pitScouting",
                                "viewMeetings",
                                "viewScoutingData",
                                "blogPosts",
                                "deleteMeetings",
                                "makeAnnouncements",
                                "makeMeetings"
                              ]
                            },
                            "phone": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "_id",
                            "firstname",
                            "lastname",
                            "username",
                            "email",
                            "roles",
                            "accountType",
                            "accountUpdateVersion",
                            "attendance",
                            "permissions",
                            "phone"
                          ]
                        }
                      ]
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "anyOf": [
                        {
                          "type": "object",
                          "properties": {
                            "_id": {
                              "type": "string"
                            },
                            "firstname": {
                              "type": "string"
                            },
                            "lastname": {
                              "type": "string"
                            },
                            "username": {
                              "type": "string"
                            },
                            "email": {
                              "type": "string"
                            },
                            "phone": {
                              "type": "string"
                            },
                            "grade": {
                              "type": "number"
                            },
                            "subteam": {
                              "type": "string"
                            },
                            "roles": {
                              "type": "array",
                              "items": {
                                "type": "string"
                              }
                            },
                            "accountType": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "_id",
                            "firstname",
                            "lastname",
                            "username",
                            "email",
                            "phone",
                            "roles",
                            "accountType"
                          ]
                        },
                        {
                          "type": "object",
                          "properties": {
                            "_id": {
                              "type": "string"
                            },
                            "firstname": {
                              "type": "string"
                            },
                            "lastname": {
                              "type": "string"
                            },
                            "username": {
                              "type": "string"
                            },
                            "email": {
                              "type": "string"
                            },
                            "subteam": {
                              "type": "string"
                            },
                            "roles": {
                              "type": "array",
                              "items": {
                                "type": "string"
                              }
                            },
                            "accountType": {
                              "type": "number"
                            },
                            "accountUpdateVersion": {
                              "type": "number"
                            },
                            "attendance": {
                              "type": "object",
                              "patternProperties": {
                                "^(.*)${'$'}": {
                                  "type": "object",
                                  "properties": {
                                    "totalHoursLogged": {
                                      "anyOf": [
                                        {
                                          "format": "numeric",
                                          "default": 0,
                                          "type": "string"
                                        },
                                        {
                                          "type": "number"
                                        }
                                      ]
                                    },
                                    "logs": {
                                      "type": "array",
                                      "items": {
                                        "type": "object",
                                        "properties": {
                                          "meetingId": {
                                            "type": "string"
                                          },
                                          "verifiedBy": {
                                            "type": "string"
                                          }
                                        },
                                        "required": [
                                          "meetingId",
                                          "verifiedBy"
                                        ]
                                      }
                                    }
                                  },
                                  "required": [
                                    "totalHoursLogged",
                                    "logs"
                                  ]
                                }
                              }
                            },
                            "grade": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "permissions": {
                              "type": "object",
                              "properties": {
                                "generalScouting": {
                                  "type": "boolean"
                                },
                                "pitScouting": {
                                  "type": "boolean"
                                },
                                "viewMeetings": {
                                  "type": "boolean"
                                },
                                "viewScoutingData": {
                                  "type": "boolean"
                                },
                                "blogPosts": {
                                  "type": "boolean"
                                },
                                "deleteMeetings": {
                                  "type": "boolean"
                                },
                                "makeAnnouncements": {
                                  "type": "boolean"
                                },
                                "makeMeetings": {
                                  "type": "boolean"
                                }
                              },
                              "required": [
                                "generalScouting",
                                "pitScouting",
                                "viewMeetings",
                                "viewScoutingData",
                                "blogPosts",
                                "deleteMeetings",
                                "makeAnnouncements",
                                "makeMeetings"
                              ]
                            },
                            "phone": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "_id",
                            "firstname",
                            "lastname",
                            "username",
                            "email",
                            "roles",
                            "accountType",
                            "accountUpdateVersion",
                            "attendance",
                            "permissions",
                            "phone"
                          ]
                        }
                      ]
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getUsersByUser",
            "description": "Look up user by username, email, or UUID. Admin access (account level 3+) required to view full data, base (level 1+) to view limited user data",
            "tags": [
              "Users"
            ]
          },
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putUsersByUser",
            "description": "Look up user by email, username, or UUID and update the user's information. Admin access (account level 3+) required",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "username": {
                        "type": "string"
                      },
                      "accountType": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "accountUpdateVersion": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "roles": {
                        "type": "array",
                        "items": {
                          "type": "object",
                          "properties": {
                            "name": {
                              "type": "string"
                            },
                            "permissions": {
                              "type": "object",
                              "properties": {
                                "generalScouting": {
                                  "type": "boolean"
                                },
                                "pitScouting": {
                                  "type": "boolean"
                                },
                                "viewMeetings": {
                                  "type": "boolean"
                                },
                                "viewScoutingData": {
                                  "type": "boolean"
                                },
                                "blogPosts": {
                                  "type": "boolean"
                                },
                                "deleteMeetings": {
                                  "type": "boolean"
                                },
                                "makeAnnouncements": {
                                  "type": "boolean"
                                },
                                "makeMeetings": {
                                  "type": "boolean"
                                }
                              },
                              "required": [
                                "generalScouting",
                                "pitScouting",
                                "viewMeetings",
                                "viewScoutingData",
                                "blogPosts",
                                "deleteMeetings",
                                "makeAnnouncements",
                                "makeMeetings"
                              ]
                            },
                            "id": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "name",
                            "permissions",
                            "id"
                          ]
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "username": {
                        "type": "string"
                      },
                      "accountType": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "accountUpdateVersion": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "roles": {
                        "type": "array",
                        "items": {
                          "type": "object",
                          "properties": {
                            "name": {
                              "type": "string"
                            },
                            "permissions": {
                              "type": "object",
                              "properties": {
                                "generalScouting": {
                                  "type": "boolean"
                                },
                                "pitScouting": {
                                  "type": "boolean"
                                },
                                "viewMeetings": {
                                  "type": "boolean"
                                },
                                "viewScoutingData": {
                                  "type": "boolean"
                                },
                                "blogPosts": {
                                  "type": "boolean"
                                },
                                "deleteMeetings": {
                                  "type": "boolean"
                                },
                                "makeAnnouncements": {
                                  "type": "boolean"
                                },
                                "makeMeetings": {
                                  "type": "boolean"
                                }
                              },
                              "required": [
                                "generalScouting",
                                "pitScouting",
                                "viewMeetings",
                                "viewScoutingData",
                                "blogPosts",
                                "deleteMeetings",
                                "makeAnnouncements",
                                "makeMeetings"
                              ]
                            },
                            "id": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "name",
                            "permissions",
                            "id"
                          ]
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "firstname": {
                        "type": "string"
                      },
                      "lastname": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string"
                      },
                      "phone": {
                        "type": "string"
                      },
                      "subteam": {
                        "type": "string"
                      },
                      "grade": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "username": {
                        "type": "string"
                      },
                      "accountType": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "accountUpdateVersion": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "roles": {
                        "type": "array",
                        "items": {
                          "type": "object",
                          "properties": {
                            "name": {
                              "type": "string"
                            },
                            "permissions": {
                              "type": "object",
                              "properties": {
                                "generalScouting": {
                                  "type": "boolean"
                                },
                                "pitScouting": {
                                  "type": "boolean"
                                },
                                "viewMeetings": {
                                  "type": "boolean"
                                },
                                "viewScoutingData": {
                                  "type": "boolean"
                                },
                                "blogPosts": {
                                  "type": "boolean"
                                },
                                "deleteMeetings": {
                                  "type": "boolean"
                                },
                                "makeAnnouncements": {
                                  "type": "boolean"
                                },
                                "makeMeetings": {
                                  "type": "boolean"
                                }
                              },
                              "required": [
                                "generalScouting",
                                "pitScouting",
                                "viewMeetings",
                                "viewScoutingData",
                                "blogPosts",
                                "deleteMeetings",
                                "makeAnnouncements",
                                "makeMeetings"
                              ]
                            },
                            "id": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "name",
                            "permissions",
                            "id"
                          ]
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                }
              }
            }
          },
          "delete": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "deleteUsersByUser",
            "description": "Look up user by email, username, or UUID and delete the user's account. Admin access (account level 3+) required",
            "tags": [
              "Users"
            ]
          }
        },
        "/users/{user}/verify": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putUsersByUserVerify",
            "description": "Verify a user's account. Admin access (account level 3+) required to verify any user, lead access (account level 2) required to verify users in the same subteam",
            "tags": [
              "Users"
            ]
          }
        },
        "/users/{user}/roles/assign": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putUsersByUserRolesAssign",
            "description": "Assign a list of roles to a user. Find user by UUID, username, or email. Body must be an array of roles' MongoDB UUIDs. Admin access (account level 3+) required",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/users/{user}/roles/revoke": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putUsersByUserRolesRevoke",
            "description": "Revoke a list of roles to a user. Find user by UUID, username, or email. Body must be an array of roles' MongoDB UUIDs. Admin access (account level 3+) required",
            "tags": [
              "Users"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/roles/": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "name": {
                      "type": "string"
                    },
                    "_id": {
                      "type": "string"
                    },
                    "permissions": {
                      "type": "object",
                      "properties": {
                        "generalScouting": {
                          "type": "boolean"
                        },
                        "pitScouting": {
                          "type": "boolean"
                        },
                        "viewMeetings": {
                          "type": "boolean"
                        },
                        "viewScoutingData": {
                          "type": "boolean"
                        },
                        "blogPosts": {
                          "type": "boolean"
                        },
                        "deleteMeetings": {
                          "type": "boolean"
                        },
                        "makeAnnouncements": {
                          "type": "boolean"
                        },
                        "makeMeetings": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "generalScouting",
                        "pitScouting",
                        "viewMeetings",
                        "viewScoutingData",
                        "blogPosts",
                        "deleteMeetings",
                        "makeAnnouncements",
                        "makeMeetings"
                      ]
                    }
                  },
                  "required": [
                    "name",
                    "_id",
                    "permissions"
                  ]
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "name": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          }
                        },
                        "required": [
                          "name",
                          "_id",
                          "permissions"
                        ]
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "name": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          }
                        },
                        "required": [
                          "name",
                          "_id",
                          "permissions"
                        ]
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "name": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          }
                        },
                        "required": [
                          "name",
                          "_id",
                          "permissions"
                        ]
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getRoles",
            "description": "View list of all roles. Requires account type admin (level 3+).",
            "tags": [
              "Roles"
            ]
          },
          "post": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "name": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        }
                      },
                      "required": [
                        "name",
                        "_id",
                        "permissions"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "name": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        }
                      },
                      "required": [
                        "name",
                        "_id",
                        "permissions"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "name": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        }
                      },
                      "required": [
                        "name",
                        "_id",
                        "permissions"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postRoles",
            "description": "Create a new user role. Requires account type admin (level 3+).",
            "tags": [
              "Roles"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string"
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        }
                      }
                    },
                    "required": [
                      "name",
                      "permissions"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string"
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        }
                      }
                    },
                    "required": [
                      "name",
                      "permissions"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string"
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        }
                      }
                    },
                    "required": [
                      "name",
                      "permissions"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/roles/{roleId}": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "roleId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "name": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        }
                      },
                      "required": [
                        "name",
                        "_id",
                        "permissions"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "name": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        }
                      },
                      "required": [
                        "name",
                        "_id",
                        "permissions"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "name": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        }
                      },
                      "required": [
                        "name",
                        "_id",
                        "permissions"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putRolesByRoleId",
            "description": "Update a role's name or associated permissions. Requires account type admin (level 3+). Automatically updates roles and permissions for all users who have the role",
            "tags": [
              "Roles"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string"
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string"
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string"
                      },
                      "permissions": {
                        "type": "object",
                        "properties": {
                          "generalScouting": {
                            "type": "boolean"
                          },
                          "pitScouting": {
                            "type": "boolean"
                          },
                          "viewMeetings": {
                            "type": "boolean"
                          },
                          "viewScoutingData": {
                            "type": "boolean"
                          },
                          "blogPosts": {
                            "type": "boolean"
                          },
                          "deleteMeetings": {
                            "type": "boolean"
                          },
                          "makeAnnouncements": {
                            "type": "boolean"
                          },
                          "makeMeetings": {
                            "type": "boolean"
                          }
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                }
              }
            }
          },
          "delete": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "roleId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "deleteRolesByRoleId",
            "description": "Delete a role. Requires account type admin (level 3+). Will automatically remove the role from all users who had the role.",
            "tags": [
              "Roles"
            ]
          }
        },
        "/roles/{roleId}/assign": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "roleId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "items": {
                  "type": "object",
                  "properties": {
                    "_id": {
                      "type": "string"
                    },
                    "firstname": {
                      "type": "string"
                    },
                    "lastname": {
                      "type": "string"
                    },
                    "username": {
                      "type": "string"
                    },
                    "email": {
                      "type": "string"
                    },
                    "subteam": {
                      "type": "string"
                    },
                    "roles": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    },
                    "accountType": {
                      "type": "number"
                    },
                    "accountUpdateVersion": {
                      "type": "number"
                    },
                    "attendance": {
                      "type": "object",
                      "patternProperties": {
                        "^(.*)${'$'}": {
                          "type": "object",
                          "properties": {
                            "totalHoursLogged": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "logs": {
                              "type": "array",
                              "items": {
                                "type": "object",
                                "properties": {
                                  "meetingId": {
                                    "type": "string"
                                  },
                                  "verifiedBy": {
                                    "type": "string"
                                  }
                                },
                                "required": [
                                  "meetingId",
                                  "verifiedBy"
                                ]
                              }
                            }
                          },
                          "required": [
                            "totalHoursLogged",
                            "logs"
                          ]
                        }
                      }
                    },
                    "grade": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "permissions": {
                      "type": "object",
                      "properties": {
                        "generalScouting": {
                          "type": "boolean"
                        },
                        "pitScouting": {
                          "type": "boolean"
                        },
                        "viewMeetings": {
                          "type": "boolean"
                        },
                        "viewScoutingData": {
                          "type": "boolean"
                        },
                        "blogPosts": {
                          "type": "boolean"
                        },
                        "deleteMeetings": {
                          "type": "boolean"
                        },
                        "makeAnnouncements": {
                          "type": "boolean"
                        },
                        "makeMeetings": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "generalScouting",
                        "pitScouting",
                        "viewMeetings",
                        "viewScoutingData",
                        "blogPosts",
                        "deleteMeetings",
                        "makeAnnouncements",
                        "makeMeetings"
                      ]
                    },
                    "phone": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "_id",
                    "firstname",
                    "lastname",
                    "username",
                    "email",
                    "roles",
                    "accountType",
                    "accountUpdateVersion",
                    "attendance",
                    "permissions",
                    "phone"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "phone": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "permissions",
                          "phone"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "phone": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "permissions",
                          "phone"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "phone": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "permissions",
                          "phone"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putRolesByRoleIdAssign",
            "description": "Assign a role to a list of users (accepts a mixed list of email, username, or UUID). Requires account type admin (level 3+). Returns list of updated user accounts. Aborts operation if any user update fails, and rolls back all changes",
            "tags": [
              "Roles"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/roles/{roleId}/revoke": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "roleId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "items": {
                  "type": "object",
                  "properties": {
                    "_id": {
                      "type": "string"
                    },
                    "firstname": {
                      "type": "string"
                    },
                    "lastname": {
                      "type": "string"
                    },
                    "username": {
                      "type": "string"
                    },
                    "email": {
                      "type": "string"
                    },
                    "subteam": {
                      "type": "string"
                    },
                    "roles": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    },
                    "accountType": {
                      "type": "number"
                    },
                    "accountUpdateVersion": {
                      "type": "number"
                    },
                    "attendance": {
                      "type": "object",
                      "patternProperties": {
                        "^(.*)${'$'}": {
                          "type": "object",
                          "properties": {
                            "totalHoursLogged": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "logs": {
                              "type": "array",
                              "items": {
                                "type": "object",
                                "properties": {
                                  "meetingId": {
                                    "type": "string"
                                  },
                                  "verifiedBy": {
                                    "type": "string"
                                  }
                                },
                                "required": [
                                  "meetingId",
                                  "verifiedBy"
                                ]
                              }
                            }
                          },
                          "required": [
                            "totalHoursLogged",
                            "logs"
                          ]
                        }
                      }
                    },
                    "grade": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "permissions": {
                      "type": "object",
                      "properties": {
                        "generalScouting": {
                          "type": "boolean"
                        },
                        "pitScouting": {
                          "type": "boolean"
                        },
                        "viewMeetings": {
                          "type": "boolean"
                        },
                        "viewScoutingData": {
                          "type": "boolean"
                        },
                        "blogPosts": {
                          "type": "boolean"
                        },
                        "deleteMeetings": {
                          "type": "boolean"
                        },
                        "makeAnnouncements": {
                          "type": "boolean"
                        },
                        "makeMeetings": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "generalScouting",
                        "pitScouting",
                        "viewMeetings",
                        "viewScoutingData",
                        "blogPosts",
                        "deleteMeetings",
                        "makeAnnouncements",
                        "makeMeetings"
                      ]
                    },
                    "phone": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "_id",
                    "firstname",
                    "lastname",
                    "username",
                    "email",
                    "roles",
                    "accountType",
                    "accountUpdateVersion",
                    "attendance",
                    "permissions",
                    "phone"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "phone": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "permissions",
                          "phone"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "phone": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "permissions",
                          "phone"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "firstname": {
                            "type": "string"
                          },
                          "lastname": {
                            "type": "string"
                          },
                          "username": {
                            "type": "string"
                          },
                          "email": {
                            "type": "string"
                          },
                          "subteam": {
                            "type": "string"
                          },
                          "roles": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "accountType": {
                            "type": "number"
                          },
                          "accountUpdateVersion": {
                            "type": "number"
                          },
                          "attendance": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "totalHoursLogged": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "logs": {
                                    "type": "array",
                                    "items": {
                                      "type": "object",
                                      "properties": {
                                        "meetingId": {
                                          "type": "string"
                                        },
                                        "verifiedBy": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "meetingId",
                                        "verifiedBy"
                                      ]
                                    }
                                  }
                                },
                                "required": [
                                  "totalHoursLogged",
                                  "logs"
                                ]
                              }
                            }
                          },
                          "grade": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "permissions": {
                            "type": "object",
                            "properties": {
                              "generalScouting": {
                                "type": "boolean"
                              },
                              "pitScouting": {
                                "type": "boolean"
                              },
                              "viewMeetings": {
                                "type": "boolean"
                              },
                              "viewScoutingData": {
                                "type": "boolean"
                              },
                              "blogPosts": {
                                "type": "boolean"
                              },
                              "deleteMeetings": {
                                "type": "boolean"
                              },
                              "makeAnnouncements": {
                                "type": "boolean"
                              },
                              "makeMeetings": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "generalScouting",
                              "pitScouting",
                              "viewMeetings",
                              "viewScoutingData",
                              "blogPosts",
                              "deleteMeetings",
                              "makeAnnouncements",
                              "makeMeetings"
                            ]
                          },
                          "phone": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "_id",
                          "firstname",
                          "lastname",
                          "username",
                          "email",
                          "roles",
                          "accountType",
                          "accountUpdateVersion",
                          "attendance",
                          "permissions",
                          "phone"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putRolesByRoleIdRevoke",
            "description": "Revoke a role from a list of users (accepts a mixed list of username, email, or UUID). Requires account type admin (level 3+). Returns list of updated user accounts. Aborts operation if any user update fails, and rolls back all changes",
            "tags": [
              "Roles"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/attendance/meetings": {
          "post": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "201": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "type": {
                          "type": "string"
                        },
                        "description": {
                          "type": "string"
                        },
                        "startTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "endTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "value": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "attendancePeriod": {
                          "type": "string"
                        },
                        "isArchived": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "_id",
                        "type",
                        "description",
                        "startTime",
                        "endTime",
                        "value",
                        "createdBy",
                        "attendancePeriod",
                        "isArchived"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "type": {
                          "type": "string"
                        },
                        "description": {
                          "type": "string"
                        },
                        "startTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "endTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "value": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "attendancePeriod": {
                          "type": "string"
                        },
                        "isArchived": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "_id",
                        "type",
                        "description",
                        "startTime",
                        "endTime",
                        "value",
                        "createdBy",
                        "attendancePeriod",
                        "isArchived"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "type": {
                          "type": "string"
                        },
                        "description": {
                          "type": "string"
                        },
                        "startTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "endTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "value": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "attendancePeriod": {
                          "type": "string"
                        },
                        "isArchived": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "_id",
                        "type",
                        "description",
                        "startTime",
                        "endTime",
                        "value",
                        "createdBy",
                        "attendancePeriod",
                        "isArchived"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postAttendanceMeetings",
            "description": "Create a new meeting. Requires account type lead (level 2+) and permission to create meetings.",
            "tags": [
              "Attendance"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "type": {
                        "type": "string"
                      },
                      "description": {
                        "type": "string"
                      },
                      "startTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "endTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "value": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "attendancePeriod": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "type",
                      "description",
                      "startTime",
                      "endTime",
                      "value",
                      "attendancePeriod"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "type": {
                        "type": "string"
                      },
                      "description": {
                        "type": "string"
                      },
                      "startTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "endTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "value": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "attendancePeriod": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "type",
                      "description",
                      "startTime",
                      "endTime",
                      "value",
                      "attendancePeriod"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "type": {
                        "type": "string"
                      },
                      "description": {
                        "type": "string"
                      },
                      "startTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "endTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "value": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "attendancePeriod": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "type",
                      "description",
                      "startTime",
                      "endTime",
                      "value",
                      "attendancePeriod"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          },
          "get": {
            "operationId": "getAttendanceMeetings",
            "description": "Redirects to /attendance/meetings/info",
            "tags": [
              "Attendance"
            ],
            "responses": {
              "200": {

              }
            }
          }
        },
        "/attendance/meetings/info": {
          "get": {
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "type": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "startTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "endTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "value": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "attendancePeriod": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "type",
                    "description",
                    "startTime",
                    "endTime",
                    "value",
                    "attendancePeriod"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getAttendanceMeetingsInfo",
            "description": "Get information about non-archived meetings. Filter by query parameters.",
            "tags": [
              "Attendance"
            ]
          }
        },
        "/attendance/meetings/all": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "_id": {
                      "type": "string"
                    },
                    "type": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "startTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "endTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "value": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "createdBy": {
                      "type": "string"
                    },
                    "attendancePeriod": {
                      "type": "string"
                    },
                    "isArchived": {
                      "type": "boolean"
                    }
                  },
                  "required": [
                    "_id",
                    "type",
                    "description",
                    "startTime",
                    "endTime",
                    "value",
                    "createdBy",
                    "attendancePeriod",
                    "isArchived"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "attendancePeriod": {
                            "type": "string"
                          },
                          "isArchived": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "_id",
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "createdBy",
                          "attendancePeriod",
                          "isArchived"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "attendancePeriod": {
                            "type": "string"
                          },
                          "isArchived": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "_id",
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "createdBy",
                          "attendancePeriod",
                          "isArchived"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "attendancePeriod": {
                            "type": "string"
                          },
                          "isArchived": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "_id",
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "createdBy",
                          "attendancePeriod",
                          "isArchived"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getAttendanceMeetingsAll",
            "description": "Get all meetings. Requires account type admin (level 3+).",
            "tags": [
              "Attendance"
            ]
          }
        },
        "/attendance/meetings/current": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "_id": {
                      "type": "string"
                    },
                    "type": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "startTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "endTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "value": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "createdBy": {
                      "type": "string"
                    },
                    "attendancePeriod": {
                      "type": "string"
                    },
                    "isArchived": {
                      "type": "boolean"
                    }
                  },
                  "required": [
                    "_id",
                    "type",
                    "description",
                    "startTime",
                    "endTime",
                    "value",
                    "createdBy",
                    "attendancePeriod",
                    "isArchived"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "attendancePeriod": {
                            "type": "string"
                          },
                          "isArchived": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "_id",
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "createdBy",
                          "attendancePeriod",
                          "isArchived"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "attendancePeriod": {
                            "type": "string"
                          },
                          "isArchived": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "_id",
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "createdBy",
                          "attendancePeriod",
                          "isArchived"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "_id": {
                            "type": "string"
                          },
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "attendancePeriod": {
                            "type": "string"
                          },
                          "isArchived": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "_id",
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "createdBy",
                          "attendancePeriod",
                          "isArchived"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getAttendanceMeetingsCurrent",
            "description": "Get all meetings that are not archived and have not ended. Requires account type lead (level 2+) and permission to view meetings.",
            "tags": [
              "Attendance"
            ]
          }
        },
        "/attendance/meetings/{meetingId}": {
          "delete": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "meetingId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "deleteAttendanceMeetingsByMeetingId",
            "description": "Delete a meeting. Requires account type lead (level 2+) and permission to delete meetings.",
            "tags": [
              "Attendance"
            ]
          },
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "meetingId",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "type": {
                          "type": "string"
                        },
                        "description": {
                          "type": "string"
                        },
                        "startTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "endTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "value": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "attendancePeriod": {
                          "type": "string"
                        },
                        "isArchived": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "_id",
                        "type",
                        "description",
                        "startTime",
                        "endTime",
                        "value",
                        "createdBy",
                        "attendancePeriod",
                        "isArchived"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "type": {
                          "type": "string"
                        },
                        "description": {
                          "type": "string"
                        },
                        "startTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "endTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "value": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "attendancePeriod": {
                          "type": "string"
                        },
                        "isArchived": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "_id",
                        "type",
                        "description",
                        "startTime",
                        "endTime",
                        "value",
                        "createdBy",
                        "attendancePeriod",
                        "isArchived"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "type": {
                          "type": "string"
                        },
                        "description": {
                          "type": "string"
                        },
                        "startTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "endTime": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "value": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "attendancePeriod": {
                          "type": "string"
                        },
                        "isArchived": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "_id",
                        "type",
                        "description",
                        "startTime",
                        "endTime",
                        "value",
                        "createdBy",
                        "attendancePeriod",
                        "isArchived"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putAttendanceMeetingsByMeetingId",
            "description": "Edit a meeting. Requires account type admin (level 3+).",
            "tags": [
              "Attendance"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "type": {
                        "type": "string"
                      },
                      "description": {
                        "type": "string"
                      },
                      "startTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "endTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "value": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "attendancePeriod": {
                        "type": "string"
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "type": {
                        "type": "string"
                      },
                      "description": {
                        "type": "string"
                      },
                      "startTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "endTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "value": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "attendancePeriod": {
                        "type": "string"
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "type": {
                        "type": "string"
                      },
                      "description": {
                        "type": "string"
                      },
                      "startTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "endTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "value": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "attendancePeriod": {
                        "type": "string"
                      }
                    },
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/attendance/meetings/archive/{meetingId}": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "meetingId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putAttendanceMeetingsArchiveByMeetingId",
            "description": "Toggle if the meeting is archived. Requires account type admin (level 3+).",
            "tags": [
              "Attendance"
            ]
          }
        },
        "/attendance/meetings/attend/{meetingId}": {
          "post": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "meetingId",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "firstname": {
                          "type": "string"
                        },
                        "lastname": {
                          "type": "string"
                        },
                        "username": {
                          "type": "string"
                        },
                        "email": {
                          "type": "string"
                        },
                        "subteam": {
                          "type": "string"
                        },
                        "roles": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "accountType": {
                          "type": "number"
                        },
                        "token": {
                          "type": "string"
                        },
                        "accountUpdateVersion": {
                          "type": "number"
                        },
                        "attendance": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "totalHoursLogged": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "logs": {
                                  "type": "array",
                                  "items": {
                                    "type": "object",
                                    "properties": {
                                      "meetingId": {
                                        "type": "string"
                                      },
                                      "verifiedBy": {
                                        "type": "string"
                                      }
                                    },
                                    "required": [
                                      "meetingId",
                                      "verifiedBy"
                                    ]
                                  }
                                }
                              },
                              "required": [
                                "totalHoursLogged",
                                "logs"
                              ]
                            }
                          }
                        },
                        "grade": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "permissions": {
                          "type": "object",
                          "properties": {
                            "generalScouting": {
                              "type": "boolean"
                            },
                            "pitScouting": {
                              "type": "boolean"
                            },
                            "viewMeetings": {
                              "type": "boolean"
                            },
                            "viewScoutingData": {
                              "type": "boolean"
                            },
                            "blogPosts": {
                              "type": "boolean"
                            },
                            "deleteMeetings": {
                              "type": "boolean"
                            },
                            "makeAnnouncements": {
                              "type": "boolean"
                            },
                            "makeMeetings": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "generalScouting",
                            "pitScouting",
                            "viewMeetings",
                            "viewScoutingData",
                            "blogPosts",
                            "deleteMeetings",
                            "makeAnnouncements",
                            "makeMeetings"
                          ]
                        },
                        "phone": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "firstname",
                        "lastname",
                        "username",
                        "email",
                        "roles",
                        "accountType",
                        "token",
                        "accountUpdateVersion",
                        "attendance",
                        "permissions",
                        "phone"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postAttendanceMeetingsAttendByMeetingId",
            "description": "Log attendance for a meeting. Returns updated user info with new attendance logs.",
            "tags": [
              "Attendance"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "tapTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "verifiedBy": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "tapTime",
                      "verifiedBy"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "tapTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "verifiedBy": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "tapTime",
                      "verifiedBy"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "tapTime": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "verifiedBy": {
                        "type": "string"
                      }
                    },
                    "required": [
                      "tapTime",
                      "verifiedBy"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/attendance/logs/me": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "type": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "startTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "endTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "value": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "attendancePeriod": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "type",
                    "description",
                    "startTime",
                    "endTime",
                    "value",
                    "attendancePeriod"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getAttendanceLogsMe",
            "description": "Get info about meetings attended by the currently authenticated user",
            "tags": [
              "Attendance"
            ]
          }
        },
        "/attendance/logs/{user}": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "user",
                "required": true
              },
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "type": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "startTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "endTime": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "value": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "attendancePeriod": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "type",
                    "description",
                    "startTime",
                    "endTime",
                    "value",
                    "attendancePeriod"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string"
                          },
                          "description": {
                            "type": "string"
                          },
                          "startTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "endTime": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "value": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "attendancePeriod": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "type",
                          "description",
                          "startTime",
                          "endTime",
                          "value",
                          "attendancePeriod"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getAttendanceLogsByUser",
            "description": "Get info about meetings attended by a user. Look up user by username, email, or UUID. Admin access (account level 3+) required.",
            "tags": [
              "Attendance"
            ]
          }
        },
        "/seasons/": {
          "get": {
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "year": {
                      "type": "number"
                    },
                    "name": {
                      "type": "string"
                    },
                    "competitions": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    },
                    "attendancePeriods": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  },
                  "required": [
                    "year",
                    "name",
                    "competitions",
                    "attendancePeriods"
                  ]
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "year": {
                            "type": "number"
                          },
                          "name": {
                            "type": "string"
                          },
                          "competitions": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "attendancePeriods": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          }
                        },
                        "required": [
                          "year",
                          "name",
                          "competitions",
                          "attendancePeriods"
                        ]
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "year": {
                            "type": "number"
                          },
                          "name": {
                            "type": "string"
                          },
                          "competitions": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "attendancePeriods": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          }
                        },
                        "required": [
                          "year",
                          "name",
                          "competitions",
                          "attendancePeriods"
                        ]
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "year": {
                            "type": "number"
                          },
                          "name": {
                            "type": "string"
                          },
                          "competitions": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          },
                          "attendancePeriods": {
                            "type": "array",
                            "items": {
                              "type": "string"
                            }
                          }
                        },
                        "required": [
                          "year",
                          "name",
                          "competitions",
                          "attendancePeriods"
                        ]
                      }
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getSeasons",
            "description": "Get all seasons. Filter with query search params.",
            "tags": [
              "Seasons"
            ]
          },
          "post": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postSeasons",
            "description": "Create a season record. Requires Admin (Account level 3+). Season records contain the year, name, competitions your tema will attend, and attendance periods. Competitions and attendance periods can be added or removed after creation.",
            "tags": [
              "Seasons"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "year": {
                        "type": "number"
                      },
                      "name": {
                        "type": "string"
                      },
                      "competitions": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "attendancePeriods": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "required": [
                      "year",
                      "name",
                      "competitions",
                      "attendancePeriods"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "year": {
                        "type": "number"
                      },
                      "name": {
                        "type": "string"
                      },
                      "competitions": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "attendancePeriods": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "required": [
                      "year",
                      "name",
                      "competitions",
                      "attendancePeriods"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "year": {
                        "type": "number"
                      },
                      "name": {
                        "type": "string"
                      },
                      "competitions": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "attendancePeriods": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "required": [
                      "year",
                      "name",
                      "competitions",
                      "attendancePeriods"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/seasons/{year}": {
          "delete": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              },
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "deleteSeasonsByYear",
            "description": "Delete a season. Requires Admin (Account level 3+)",
            "tags": [
              "Seasons"
            ]
          },
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              },
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putSeasonsByYear",
            "description": "Update a season record. Requires Admin (Account level 3+).",
            "tags": [
              "Seasons"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "year": {
                        "type": "number"
                      },
                      "name": {
                        "type": "string"
                      },
                      "competitions": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "attendancePeriods": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "year": {
                        "type": "number"
                      },
                      "name": {
                        "type": "string"
                      },
                      "competitions": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "attendancePeriods": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "year": {
                        "type": "number"
                      },
                      "name": {
                        "type": "string"
                      },
                      "competitions": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      },
                      "attendancePeriods": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "additionalProperties": false
                  }
                }
              }
            }
          }
        },
        "/seasons/{year}/competitions": {
          "get": {
            "parameters": [
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "string"
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getSeasonsByYearCompetitions",
            "description": "Get a list of competitions your team will be attending for a season",
            "tags": [
              "Seasons"
            ]
          }
        },
        "/seasons/{year}/competitions/add": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              },
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putSeasonsByYearCompetitionsAdd",
            "description": "Add one or more competitions to the list for your team's season. Requires Admin (Account level 3+). The list of competitions in the season record will determine what competitions are available to scout for in the companion app.",
            "tags": [
              "Seasons"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/seasons/{year}/competitions/remove": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              },
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putSeasonsByYearCompetitionsRemove",
            "description": "Remove one or more competitions from the list for your team's season. Requires Admin (Account level 3+). The list of competitions in the season record will determine what competitions are available to scout for in the companion app.",
            "tags": [
              "Seasons"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/seasons/{year}/attendance": {
          "get": {
            "parameters": [
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "string"
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "string"
                      }
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getSeasonsByYearAttendance",
            "description": "Get a list of attendance periods for a season",
            "tags": [
              "Seasons"
            ]
          }
        },
        "/seasons/{year}/attendance/add": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              },
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putSeasonsByYearAttendanceAdd",
            "description": "Add one or more attendance periods to the list for your team's season. Requires Admin (Account level 3+). The list of attendance periods in the season record will determine what attendance periods meetings can be scheduled for in the app",
            "tags": [
              "Seasons"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/seasons/{year}/attendance/remove": {
          "put": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              },
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "year",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "year": {
                          "type": "number"
                        },
                        "name": {
                          "type": "string"
                        },
                        "competitions": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        },
                        "attendancePeriods": {
                          "type": "array",
                          "items": {
                            "type": "string"
                          }
                        }
                      },
                      "required": [
                        "year",
                        "name",
                        "competitions",
                        "attendancePeriods"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "putSeasonsByYearAttendanceRemove",
            "description": "Remove one or more attendance periods from the list for your team's season. Requires Admin (Account level 3+). The list of attendance periods in the season record will determine what attendance periods meetings can be scheduled for in the app",
            "tags": [
              "Seasons"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          }
        },
        "/crescendo/": {
          "post": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "auto": {
                          "type": "object",
                          "properties": {
                            "leave": {
                              "type": "boolean"
                            },
                            "attempted": {
                              "type": "number"
                            },
                            "scored": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "leave",
                            "attempted",
                            "scored"
                          ]
                        },
                        "comments": {
                          "type": "string"
                        },
                        "competition": {
                          "type": "string"
                        },
                        "defensive": {
                          "type": "boolean"
                        },
                        "matchNumber": {
                          "type": "number"
                        },
                        "penaltyPointsEarned": {
                          "type": "number"
                        },
                        "ranking": {
                          "type": "object",
                          "properties": {
                            "melody": {
                              "type": "boolean"
                            },
                            "ensemble": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "melody",
                            "ensemble"
                          ]
                        },
                        "rankingPoints": {
                          "type": "number"
                        },
                        "score": {
                          "type": "number"
                        },
                        "stage": {
                          "type": "object",
                          "properties": {
                            "state": {
                              "anyOf": [
                                {
                                  "const": "NOT_PARKED",
                                  "type": "string"
                                },
                                {
                                  "const": "PARKED",
                                  "type": "string"
                                },
                                {
                                  "const": "ONSTAGE",
                                  "type": "string"
                                },
                                {
                                  "const": "ONSTAGE_SPOTLIT",
                                  "type": "string"
                                }
                              ]
                            },
                            "harmony": {
                              "type": "number"
                            },
                            "trap": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "state",
                            "harmony",
                            "trap"
                          ]
                        },
                        "teamNumber": {
                          "type": "number"
                        },
                        "teleop": {
                          "type": "object",
                          "properties": {
                            "ampNotes": {
                              "type": "number"
                            },
                            "speakerUnamped": {
                              "type": "number"
                            },
                            "speakerAmped": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "ampNotes",
                            "speakerUnamped",
                            "speakerAmped"
                          ]
                        },
                        "tied": {
                          "type": "boolean"
                        },
                        "won": {
                          "type": "boolean"
                        },
                        "brokeDown": {
                          "type": "boolean"
                        },
                        "teamName": {
                          "type": "string"
                        },
                        "createdAt": {
                          "type": "string"
                        },
                        "updatedAt": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "rating": {
                          "type": "number"
                        },
                        "defenseRating": {
                          "type": "number"
                        },
                        "human": {
                          "type": "object",
                          "properties": {
                            "source": {
                              "type": "boolean"
                            },
                            "rating": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "source",
                            "rating"
                          ]
                        },
                        "coopertition": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "auto",
                        "comments",
                        "competition",
                        "defensive",
                        "matchNumber",
                        "penaltyPointsEarned",
                        "ranking",
                        "rankingPoints",
                        "score",
                        "stage",
                        "teamNumber",
                        "teleop",
                        "tied",
                        "won",
                        "brokeDown",
                        "teamName",
                        "createdAt",
                        "updatedAt",
                        "_id",
                        "createdBy",
                        "rating",
                        "defenseRating",
                        "human",
                        "coopertition"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "auto": {
                          "type": "object",
                          "properties": {
                            "leave": {
                              "type": "boolean"
                            },
                            "attempted": {
                              "type": "number"
                            },
                            "scored": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "leave",
                            "attempted",
                            "scored"
                          ]
                        },
                        "comments": {
                          "type": "string"
                        },
                        "competition": {
                          "type": "string"
                        },
                        "defensive": {
                          "type": "boolean"
                        },
                        "matchNumber": {
                          "type": "number"
                        },
                        "penaltyPointsEarned": {
                          "type": "number"
                        },
                        "ranking": {
                          "type": "object",
                          "properties": {
                            "melody": {
                              "type": "boolean"
                            },
                            "ensemble": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "melody",
                            "ensemble"
                          ]
                        },
                        "rankingPoints": {
                          "type": "number"
                        },
                        "score": {
                          "type": "number"
                        },
                        "stage": {
                          "type": "object",
                          "properties": {
                            "state": {
                              "anyOf": [
                                {
                                  "const": "NOT_PARKED",
                                  "type": "string"
                                },
                                {
                                  "const": "PARKED",
                                  "type": "string"
                                },
                                {
                                  "const": "ONSTAGE",
                                  "type": "string"
                                },
                                {
                                  "const": "ONSTAGE_SPOTLIT",
                                  "type": "string"
                                }
                              ]
                            },
                            "harmony": {
                              "type": "number"
                            },
                            "trap": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "state",
                            "harmony",
                            "trap"
                          ]
                        },
                        "teamNumber": {
                          "type": "number"
                        },
                        "teleop": {
                          "type": "object",
                          "properties": {
                            "ampNotes": {
                              "type": "number"
                            },
                            "speakerUnamped": {
                              "type": "number"
                            },
                            "speakerAmped": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "ampNotes",
                            "speakerUnamped",
                            "speakerAmped"
                          ]
                        },
                        "tied": {
                          "type": "boolean"
                        },
                        "won": {
                          "type": "boolean"
                        },
                        "brokeDown": {
                          "type": "boolean"
                        },
                        "teamName": {
                          "type": "string"
                        },
                        "createdAt": {
                          "type": "string"
                        },
                        "updatedAt": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "rating": {
                          "type": "number"
                        },
                        "defenseRating": {
                          "type": "number"
                        },
                        "human": {
                          "type": "object",
                          "properties": {
                            "source": {
                              "type": "boolean"
                            },
                            "rating": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "source",
                            "rating"
                          ]
                        },
                        "coopertition": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "auto",
                        "comments",
                        "competition",
                        "defensive",
                        "matchNumber",
                        "penaltyPointsEarned",
                        "ranking",
                        "rankingPoints",
                        "score",
                        "stage",
                        "teamNumber",
                        "teleop",
                        "tied",
                        "won",
                        "brokeDown",
                        "teamName",
                        "createdAt",
                        "updatedAt",
                        "_id",
                        "createdBy",
                        "rating",
                        "defenseRating",
                        "human",
                        "coopertition"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "auto": {
                          "type": "object",
                          "properties": {
                            "leave": {
                              "type": "boolean"
                            },
                            "attempted": {
                              "type": "number"
                            },
                            "scored": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "leave",
                            "attempted",
                            "scored"
                          ]
                        },
                        "comments": {
                          "type": "string"
                        },
                        "competition": {
                          "type": "string"
                        },
                        "defensive": {
                          "type": "boolean"
                        },
                        "matchNumber": {
                          "type": "number"
                        },
                        "penaltyPointsEarned": {
                          "type": "number"
                        },
                        "ranking": {
                          "type": "object",
                          "properties": {
                            "melody": {
                              "type": "boolean"
                            },
                            "ensemble": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "melody",
                            "ensemble"
                          ]
                        },
                        "rankingPoints": {
                          "type": "number"
                        },
                        "score": {
                          "type": "number"
                        },
                        "stage": {
                          "type": "object",
                          "properties": {
                            "state": {
                              "anyOf": [
                                {
                                  "const": "NOT_PARKED",
                                  "type": "string"
                                },
                                {
                                  "const": "PARKED",
                                  "type": "string"
                                },
                                {
                                  "const": "ONSTAGE",
                                  "type": "string"
                                },
                                {
                                  "const": "ONSTAGE_SPOTLIT",
                                  "type": "string"
                                }
                              ]
                            },
                            "harmony": {
                              "type": "number"
                            },
                            "trap": {
                              "type": "boolean"
                            }
                          },
                          "required": [
                            "state",
                            "harmony",
                            "trap"
                          ]
                        },
                        "teamNumber": {
                          "type": "number"
                        },
                        "teleop": {
                          "type": "object",
                          "properties": {
                            "ampNotes": {
                              "type": "number"
                            },
                            "speakerUnamped": {
                              "type": "number"
                            },
                            "speakerAmped": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "ampNotes",
                            "speakerUnamped",
                            "speakerAmped"
                          ]
                        },
                        "tied": {
                          "type": "boolean"
                        },
                        "won": {
                          "type": "boolean"
                        },
                        "brokeDown": {
                          "type": "boolean"
                        },
                        "teamName": {
                          "type": "string"
                        },
                        "createdAt": {
                          "type": "string"
                        },
                        "updatedAt": {
                          "type": "string"
                        },
                        "_id": {
                          "type": "string"
                        },
                        "createdBy": {
                          "type": "string"
                        },
                        "rating": {
                          "type": "number"
                        },
                        "defenseRating": {
                          "type": "number"
                        },
                        "human": {
                          "type": "object",
                          "properties": {
                            "source": {
                              "type": "boolean"
                            },
                            "rating": {
                              "type": "number"
                            }
                          },
                          "required": [
                            "source",
                            "rating"
                          ]
                        },
                        "coopertition": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "auto",
                        "comments",
                        "competition",
                        "defensive",
                        "matchNumber",
                        "penaltyPointsEarned",
                        "ranking",
                        "rankingPoints",
                        "score",
                        "stage",
                        "teamNumber",
                        "teleop",
                        "tied",
                        "won",
                        "brokeDown",
                        "teamName",
                        "createdAt",
                        "updatedAt",
                        "_id",
                        "createdBy",
                        "rating",
                        "defenseRating",
                        "human",
                        "coopertition"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "400": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postCrescendo",
            "description": "Submit a scouting form for the Crescendo game. Requries base verification (account level 1+) and permission to submit general scouting forms.",
            "tags": [
              "Crescendo",
              "Scouting"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "auto": {
                        "type": "object",
                        "properties": {
                          "leave": {
                            "type": "boolean"
                          },
                          "attempted": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "scored": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "leave",
                          "attempted",
                          "scored"
                        ]
                      },
                      "comments": {
                        "type": "string"
                      },
                      "competition": {
                        "type": "string"
                      },
                      "defensive": {
                        "type": "boolean"
                      },
                      "matchNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "penaltyPointsEarned": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "ranking": {
                        "type": "object",
                        "properties": {
                          "melody": {
                            "type": "boolean"
                          },
                          "ensemble": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "melody",
                          "ensemble"
                        ]
                      },
                      "rankingPoints": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "score": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "stage": {
                        "type": "object",
                        "properties": {
                          "state": {
                            "anyOf": [
                              {
                                "const": "NOT_PARKED",
                                "type": "string"
                              },
                              {
                                "const": "PARKED",
                                "type": "string"
                              },
                              {
                                "const": "ONSTAGE",
                                "type": "string"
                              },
                              {
                                "const": "ONSTAGE_SPOTLIT",
                                "type": "string"
                              }
                            ]
                          },
                          "harmony": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "trap": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "state",
                          "harmony",
                          "trap"
                        ]
                      },
                      "teamNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "teleop": {
                        "type": "object",
                        "properties": {
                          "ampNotes": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "speakerUnamped": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "speakerAmped": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "ampNotes",
                          "speakerUnamped",
                          "speakerAmped"
                        ]
                      },
                      "tied": {
                        "type": "boolean"
                      },
                      "won": {
                        "type": "boolean"
                      },
                      "brokeDown": {
                        "type": "boolean"
                      },
                      "rating": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "defenseRating": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "human": {
                        "type": "object",
                        "properties": {
                          "source": {
                            "type": "boolean"
                          },
                          "rating": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "source",
                          "rating"
                        ]
                      },
                      "coopertition": {
                        "type": "boolean"
                      }
                    },
                    "required": [
                      "auto",
                      "competition",
                      "defensive",
                      "matchNumber",
                      "penaltyPointsEarned",
                      "ranking",
                      "rankingPoints",
                      "score",
                      "stage",
                      "teamNumber",
                      "teleop",
                      "tied",
                      "won",
                      "brokeDown",
                      "rating",
                      "defenseRating",
                      "human",
                      "coopertition"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "auto": {
                        "type": "object",
                        "properties": {
                          "leave": {
                            "type": "boolean"
                          },
                          "attempted": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "scored": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "leave",
                          "attempted",
                          "scored"
                        ]
                      },
                      "comments": {
                        "type": "string"
                      },
                      "competition": {
                        "type": "string"
                      },
                      "defensive": {
                        "type": "boolean"
                      },
                      "matchNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "penaltyPointsEarned": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "ranking": {
                        "type": "object",
                        "properties": {
                          "melody": {
                            "type": "boolean"
                          },
                          "ensemble": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "melody",
                          "ensemble"
                        ]
                      },
                      "rankingPoints": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "score": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "stage": {
                        "type": "object",
                        "properties": {
                          "state": {
                            "anyOf": [
                              {
                                "const": "NOT_PARKED",
                                "type": "string"
                              },
                              {
                                "const": "PARKED",
                                "type": "string"
                              },
                              {
                                "const": "ONSTAGE",
                                "type": "string"
                              },
                              {
                                "const": "ONSTAGE_SPOTLIT",
                                "type": "string"
                              }
                            ]
                          },
                          "harmony": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "trap": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "state",
                          "harmony",
                          "trap"
                        ]
                      },
                      "teamNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "teleop": {
                        "type": "object",
                        "properties": {
                          "ampNotes": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "speakerUnamped": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "speakerAmped": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "ampNotes",
                          "speakerUnamped",
                          "speakerAmped"
                        ]
                      },
                      "tied": {
                        "type": "boolean"
                      },
                      "won": {
                        "type": "boolean"
                      },
                      "brokeDown": {
                        "type": "boolean"
                      },
                      "rating": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "defenseRating": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "human": {
                        "type": "object",
                        "properties": {
                          "source": {
                            "type": "boolean"
                          },
                          "rating": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "source",
                          "rating"
                        ]
                      },
                      "coopertition": {
                        "type": "boolean"
                      }
                    },
                    "required": [
                      "auto",
                      "competition",
                      "defensive",
                      "matchNumber",
                      "penaltyPointsEarned",
                      "ranking",
                      "rankingPoints",
                      "score",
                      "stage",
                      "teamNumber",
                      "teleop",
                      "tied",
                      "won",
                      "brokeDown",
                      "rating",
                      "defenseRating",
                      "human",
                      "coopertition"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "auto": {
                        "type": "object",
                        "properties": {
                          "leave": {
                            "type": "boolean"
                          },
                          "attempted": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "scored": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "leave",
                          "attempted",
                          "scored"
                        ]
                      },
                      "comments": {
                        "type": "string"
                      },
                      "competition": {
                        "type": "string"
                      },
                      "defensive": {
                        "type": "boolean"
                      },
                      "matchNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "penaltyPointsEarned": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "ranking": {
                        "type": "object",
                        "properties": {
                          "melody": {
                            "type": "boolean"
                          },
                          "ensemble": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "melody",
                          "ensemble"
                        ]
                      },
                      "rankingPoints": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "score": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "stage": {
                        "type": "object",
                        "properties": {
                          "state": {
                            "anyOf": [
                              {
                                "const": "NOT_PARKED",
                                "type": "string"
                              },
                              {
                                "const": "PARKED",
                                "type": "string"
                              },
                              {
                                "const": "ONSTAGE",
                                "type": "string"
                              },
                              {
                                "const": "ONSTAGE_SPOTLIT",
                                "type": "string"
                              }
                            ]
                          },
                          "harmony": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "trap": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "state",
                          "harmony",
                          "trap"
                        ]
                      },
                      "teamNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "teleop": {
                        "type": "object",
                        "properties": {
                          "ampNotes": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "speakerUnamped": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "speakerAmped": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "ampNotes",
                          "speakerUnamped",
                          "speakerAmped"
                        ]
                      },
                      "tied": {
                        "type": "boolean"
                      },
                      "won": {
                        "type": "boolean"
                      },
                      "brokeDown": {
                        "type": "boolean"
                      },
                      "rating": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "defenseRating": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "human": {
                        "type": "object",
                        "properties": {
                          "source": {
                            "type": "boolean"
                          },
                          "rating": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          }
                        },
                        "required": [
                          "source",
                          "rating"
                        ]
                      },
                      "coopertition": {
                        "type": "boolean"
                      }
                    },
                    "required": [
                      "auto",
                      "competition",
                      "defensive",
                      "matchNumber",
                      "penaltyPointsEarned",
                      "ranking",
                      "rankingPoints",
                      "score",
                      "stage",
                      "teamNumber",
                      "teleop",
                      "tied",
                      "won",
                      "brokeDown",
                      "rating",
                      "defenseRating",
                      "human",
                      "coopertition"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          },
          "get": {
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "auto": {
                      "type": "object",
                      "properties": {
                        "leave": {
                          "type": "boolean"
                        },
                        "attempted": {
                          "type": "number"
                        },
                        "scored": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "leave",
                        "attempted",
                        "scored"
                      ]
                    },
                    "comments": {
                      "type": "string"
                    },
                    "competition": {
                      "type": "string"
                    },
                    "defensive": {
                      "type": "boolean"
                    },
                    "matchNumber": {
                      "type": "number"
                    },
                    "penaltyPointsEarned": {
                      "type": "number"
                    },
                    "ranking": {
                      "type": "object",
                      "properties": {
                        "melody": {
                          "type": "boolean"
                        },
                        "ensemble": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "melody",
                        "ensemble"
                      ]
                    },
                    "rankingPoints": {
                      "type": "number"
                    },
                    "score": {
                      "type": "number"
                    },
                    "stage": {
                      "type": "object",
                      "properties": {
                        "state": {
                          "anyOf": [
                            {
                              "const": "NOT_PARKED",
                              "type": "string"
                            },
                            {
                              "const": "PARKED",
                              "type": "string"
                            },
                            {
                              "const": "ONSTAGE",
                              "type": "string"
                            },
                            {
                              "const": "ONSTAGE_SPOTLIT",
                              "type": "string"
                            }
                          ]
                        },
                        "harmony": {
                          "type": "number"
                        },
                        "trap": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "state",
                        "harmony",
                        "trap"
                      ]
                    },
                    "teamNumber": {
                      "type": "number"
                    },
                    "teleop": {
                      "type": "object",
                      "properties": {
                        "ampNotes": {
                          "type": "number"
                        },
                        "speakerUnamped": {
                          "type": "number"
                        },
                        "speakerAmped": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "ampNotes",
                        "speakerUnamped",
                        "speakerAmped"
                      ]
                    },
                    "tied": {
                      "type": "boolean"
                    },
                    "won": {
                      "type": "boolean"
                    },
                    "brokeDown": {
                      "type": "boolean"
                    },
                    "teamName": {
                      "type": "string"
                    },
                    "createdAt": {
                      "type": "string"
                    },
                    "updatedAt": {
                      "type": "string"
                    },
                    "_id": {
                      "type": "string"
                    },
                    "createdBy": {
                      "type": "string"
                    },
                    "rating": {
                      "type": "number"
                    },
                    "defenseRating": {
                      "type": "number"
                    },
                    "human": {
                      "type": "object",
                      "properties": {
                        "source": {
                          "type": "boolean"
                        },
                        "rating": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "source",
                        "rating"
                      ]
                    },
                    "coopertition": {
                      "type": "boolean"
                    }
                  },
                  "required": [
                    "auto",
                    "comments",
                    "competition",
                    "defensive",
                    "matchNumber",
                    "penaltyPointsEarned",
                    "ranking",
                    "rankingPoints",
                    "score",
                    "stage",
                    "teamNumber",
                    "teleop",
                    "tied",
                    "won",
                    "brokeDown",
                    "teamName",
                    "createdAt",
                    "updatedAt",
                    "_id",
                    "createdBy",
                    "rating",
                    "defenseRating",
                    "human",
                    "coopertition"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "auto": {
                            "type": "object",
                            "properties": {
                              "leave": {
                                "type": "boolean"
                              },
                              "attempted": {
                                "type": "number"
                              },
                              "scored": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "leave",
                              "attempted",
                              "scored"
                            ]
                          },
                          "comments": {
                            "type": "string"
                          },
                          "competition": {
                            "type": "string"
                          },
                          "defensive": {
                            "type": "boolean"
                          },
                          "matchNumber": {
                            "type": "number"
                          },
                          "penaltyPointsEarned": {
                            "type": "number"
                          },
                          "ranking": {
                            "type": "object",
                            "properties": {
                              "melody": {
                                "type": "boolean"
                              },
                              "ensemble": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "melody",
                              "ensemble"
                            ]
                          },
                          "rankingPoints": {
                            "type": "number"
                          },
                          "score": {
                            "type": "number"
                          },
                          "stage": {
                            "type": "object",
                            "properties": {
                              "state": {
                                "anyOf": [
                                  {
                                    "const": "NOT_PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE_SPOTLIT",
                                    "type": "string"
                                  }
                                ]
                              },
                              "harmony": {
                                "type": "number"
                              },
                              "trap": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "state",
                              "harmony",
                              "trap"
                            ]
                          },
                          "teamNumber": {
                            "type": "number"
                          },
                          "teleop": {
                            "type": "object",
                            "properties": {
                              "ampNotes": {
                                "type": "number"
                              },
                              "speakerUnamped": {
                                "type": "number"
                              },
                              "speakerAmped": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "ampNotes",
                              "speakerUnamped",
                              "speakerAmped"
                            ]
                          },
                          "tied": {
                            "type": "boolean"
                          },
                          "won": {
                            "type": "boolean"
                          },
                          "brokeDown": {
                            "type": "boolean"
                          },
                          "teamName": {
                            "type": "string"
                          },
                          "createdAt": {
                            "type": "string"
                          },
                          "updatedAt": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "rating": {
                            "type": "number"
                          },
                          "defenseRating": {
                            "type": "number"
                          },
                          "human": {
                            "type": "object",
                            "properties": {
                              "source": {
                                "type": "boolean"
                              },
                              "rating": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "source",
                              "rating"
                            ]
                          },
                          "coopertition": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "auto",
                          "comments",
                          "competition",
                          "defensive",
                          "matchNumber",
                          "penaltyPointsEarned",
                          "ranking",
                          "rankingPoints",
                          "score",
                          "stage",
                          "teamNumber",
                          "teleop",
                          "tied",
                          "won",
                          "brokeDown",
                          "teamName",
                          "createdAt",
                          "updatedAt",
                          "_id",
                          "createdBy",
                          "rating",
                          "defenseRating",
                          "human",
                          "coopertition"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "auto": {
                            "type": "object",
                            "properties": {
                              "leave": {
                                "type": "boolean"
                              },
                              "attempted": {
                                "type": "number"
                              },
                              "scored": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "leave",
                              "attempted",
                              "scored"
                            ]
                          },
                          "comments": {
                            "type": "string"
                          },
                          "competition": {
                            "type": "string"
                          },
                          "defensive": {
                            "type": "boolean"
                          },
                          "matchNumber": {
                            "type": "number"
                          },
                          "penaltyPointsEarned": {
                            "type": "number"
                          },
                          "ranking": {
                            "type": "object",
                            "properties": {
                              "melody": {
                                "type": "boolean"
                              },
                              "ensemble": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "melody",
                              "ensemble"
                            ]
                          },
                          "rankingPoints": {
                            "type": "number"
                          },
                          "score": {
                            "type": "number"
                          },
                          "stage": {
                            "type": "object",
                            "properties": {
                              "state": {
                                "anyOf": [
                                  {
                                    "const": "NOT_PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE_SPOTLIT",
                                    "type": "string"
                                  }
                                ]
                              },
                              "harmony": {
                                "type": "number"
                              },
                              "trap": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "state",
                              "harmony",
                              "trap"
                            ]
                          },
                          "teamNumber": {
                            "type": "number"
                          },
                          "teleop": {
                            "type": "object",
                            "properties": {
                              "ampNotes": {
                                "type": "number"
                              },
                              "speakerUnamped": {
                                "type": "number"
                              },
                              "speakerAmped": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "ampNotes",
                              "speakerUnamped",
                              "speakerAmped"
                            ]
                          },
                          "tied": {
                            "type": "boolean"
                          },
                          "won": {
                            "type": "boolean"
                          },
                          "brokeDown": {
                            "type": "boolean"
                          },
                          "teamName": {
                            "type": "string"
                          },
                          "createdAt": {
                            "type": "string"
                          },
                          "updatedAt": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "rating": {
                            "type": "number"
                          },
                          "defenseRating": {
                            "type": "number"
                          },
                          "human": {
                            "type": "object",
                            "properties": {
                              "source": {
                                "type": "boolean"
                              },
                              "rating": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "source",
                              "rating"
                            ]
                          },
                          "coopertition": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "auto",
                          "comments",
                          "competition",
                          "defensive",
                          "matchNumber",
                          "penaltyPointsEarned",
                          "ranking",
                          "rankingPoints",
                          "score",
                          "stage",
                          "teamNumber",
                          "teleop",
                          "tied",
                          "won",
                          "brokeDown",
                          "teamName",
                          "createdAt",
                          "updatedAt",
                          "_id",
                          "createdBy",
                          "rating",
                          "defenseRating",
                          "human",
                          "coopertition"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "auto": {
                            "type": "object",
                            "properties": {
                              "leave": {
                                "type": "boolean"
                              },
                              "attempted": {
                                "type": "number"
                              },
                              "scored": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "leave",
                              "attempted",
                              "scored"
                            ]
                          },
                          "comments": {
                            "type": "string"
                          },
                          "competition": {
                            "type": "string"
                          },
                          "defensive": {
                            "type": "boolean"
                          },
                          "matchNumber": {
                            "type": "number"
                          },
                          "penaltyPointsEarned": {
                            "type": "number"
                          },
                          "ranking": {
                            "type": "object",
                            "properties": {
                              "melody": {
                                "type": "boolean"
                              },
                              "ensemble": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "melody",
                              "ensemble"
                            ]
                          },
                          "rankingPoints": {
                            "type": "number"
                          },
                          "score": {
                            "type": "number"
                          },
                          "stage": {
                            "type": "object",
                            "properties": {
                              "state": {
                                "anyOf": [
                                  {
                                    "const": "NOT_PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE_SPOTLIT",
                                    "type": "string"
                                  }
                                ]
                              },
                              "harmony": {
                                "type": "number"
                              },
                              "trap": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "state",
                              "harmony",
                              "trap"
                            ]
                          },
                          "teamNumber": {
                            "type": "number"
                          },
                          "teleop": {
                            "type": "object",
                            "properties": {
                              "ampNotes": {
                                "type": "number"
                              },
                              "speakerUnamped": {
                                "type": "number"
                              },
                              "speakerAmped": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "ampNotes",
                              "speakerUnamped",
                              "speakerAmped"
                            ]
                          },
                          "tied": {
                            "type": "boolean"
                          },
                          "won": {
                            "type": "boolean"
                          },
                          "brokeDown": {
                            "type": "boolean"
                          },
                          "teamName": {
                            "type": "string"
                          },
                          "createdAt": {
                            "type": "string"
                          },
                          "updatedAt": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "rating": {
                            "type": "number"
                          },
                          "defenseRating": {
                            "type": "number"
                          },
                          "human": {
                            "type": "object",
                            "properties": {
                              "source": {
                                "type": "boolean"
                              },
                              "rating": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "source",
                              "rating"
                            ]
                          },
                          "coopertition": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "auto",
                          "comments",
                          "competition",
                          "defensive",
                          "matchNumber",
                          "penaltyPointsEarned",
                          "ranking",
                          "rankingPoints",
                          "score",
                          "stage",
                          "teamNumber",
                          "teleop",
                          "tied",
                          "won",
                          "brokeDown",
                          "teamName",
                          "createdAt",
                          "updatedAt",
                          "_id",
                          "createdBy",
                          "rating",
                          "defenseRating",
                          "human",
                          "coopertition"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "401": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "403": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getCrescendo",
            "description": "Get all scouting submissions for the Crescendo game. Requires Base verification (account level 1+) and permission to view scouting data.",
            "tags": [
              "Crescendo",
              "Scouting"
            ]
          }
        },
        "/crescendo/mine": {
          "get": {
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "header",
                "name": "authorization",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "auto": {
                      "type": "object",
                      "properties": {
                        "leave": {
                          "type": "boolean"
                        },
                        "attempted": {
                          "type": "number"
                        },
                        "scored": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "leave",
                        "attempted",
                        "scored"
                      ]
                    },
                    "comments": {
                      "type": "string"
                    },
                    "competition": {
                      "type": "string"
                    },
                    "defensive": {
                      "type": "boolean"
                    },
                    "matchNumber": {
                      "type": "number"
                    },
                    "penaltyPointsEarned": {
                      "type": "number"
                    },
                    "ranking": {
                      "type": "object",
                      "properties": {
                        "melody": {
                          "type": "boolean"
                        },
                        "ensemble": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "melody",
                        "ensemble"
                      ]
                    },
                    "rankingPoints": {
                      "type": "number"
                    },
                    "score": {
                      "type": "number"
                    },
                    "stage": {
                      "type": "object",
                      "properties": {
                        "state": {
                          "anyOf": [
                            {
                              "const": "NOT_PARKED",
                              "type": "string"
                            },
                            {
                              "const": "PARKED",
                              "type": "string"
                            },
                            {
                              "const": "ONSTAGE",
                              "type": "string"
                            },
                            {
                              "const": "ONSTAGE_SPOTLIT",
                              "type": "string"
                            }
                          ]
                        },
                        "harmony": {
                          "type": "number"
                        },
                        "trap": {
                          "type": "boolean"
                        }
                      },
                      "required": [
                        "state",
                        "harmony",
                        "trap"
                      ]
                    },
                    "teamNumber": {
                      "type": "number"
                    },
                    "teleop": {
                      "type": "object",
                      "properties": {
                        "ampNotes": {
                          "type": "number"
                        },
                        "speakerUnamped": {
                          "type": "number"
                        },
                        "speakerAmped": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "ampNotes",
                        "speakerUnamped",
                        "speakerAmped"
                      ]
                    },
                    "tied": {
                      "type": "boolean"
                    },
                    "won": {
                      "type": "boolean"
                    },
                    "brokeDown": {
                      "type": "boolean"
                    },
                    "teamName": {
                      "type": "string"
                    },
                    "createdAt": {
                      "type": "string"
                    },
                    "updatedAt": {
                      "type": "string"
                    },
                    "_id": {
                      "type": "string"
                    },
                    "createdBy": {
                      "type": "string"
                    },
                    "rating": {
                      "type": "number"
                    },
                    "defenseRating": {
                      "type": "number"
                    },
                    "human": {
                      "type": "object",
                      "properties": {
                        "source": {
                          "type": "boolean"
                        },
                        "rating": {
                          "type": "number"
                        }
                      },
                      "required": [
                        "source",
                        "rating"
                      ]
                    },
                    "coopertition": {
                      "type": "boolean"
                    }
                  },
                  "required": [
                    "auto",
                    "comments",
                    "competition",
                    "defensive",
                    "matchNumber",
                    "penaltyPointsEarned",
                    "ranking",
                    "rankingPoints",
                    "score",
                    "stage",
                    "teamNumber",
                    "teleop",
                    "tied",
                    "won",
                    "brokeDown",
                    "teamName",
                    "createdAt",
                    "updatedAt",
                    "_id",
                    "createdBy",
                    "rating",
                    "defenseRating",
                    "human",
                    "coopertition"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "auto": {
                            "type": "object",
                            "properties": {
                              "leave": {
                                "type": "boolean"
                              },
                              "attempted": {
                                "type": "number"
                              },
                              "scored": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "leave",
                              "attempted",
                              "scored"
                            ]
                          },
                          "comments": {
                            "type": "string"
                          },
                          "competition": {
                            "type": "string"
                          },
                          "defensive": {
                            "type": "boolean"
                          },
                          "matchNumber": {
                            "type": "number"
                          },
                          "penaltyPointsEarned": {
                            "type": "number"
                          },
                          "ranking": {
                            "type": "object",
                            "properties": {
                              "melody": {
                                "type": "boolean"
                              },
                              "ensemble": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "melody",
                              "ensemble"
                            ]
                          },
                          "rankingPoints": {
                            "type": "number"
                          },
                          "score": {
                            "type": "number"
                          },
                          "stage": {
                            "type": "object",
                            "properties": {
                              "state": {
                                "anyOf": [
                                  {
                                    "const": "NOT_PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE_SPOTLIT",
                                    "type": "string"
                                  }
                                ]
                              },
                              "harmony": {
                                "type": "number"
                              },
                              "trap": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "state",
                              "harmony",
                              "trap"
                            ]
                          },
                          "teamNumber": {
                            "type": "number"
                          },
                          "teleop": {
                            "type": "object",
                            "properties": {
                              "ampNotes": {
                                "type": "number"
                              },
                              "speakerUnamped": {
                                "type": "number"
                              },
                              "speakerAmped": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "ampNotes",
                              "speakerUnamped",
                              "speakerAmped"
                            ]
                          },
                          "tied": {
                            "type": "boolean"
                          },
                          "won": {
                            "type": "boolean"
                          },
                          "brokeDown": {
                            "type": "boolean"
                          },
                          "teamName": {
                            "type": "string"
                          },
                          "createdAt": {
                            "type": "string"
                          },
                          "updatedAt": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "rating": {
                            "type": "number"
                          },
                          "defenseRating": {
                            "type": "number"
                          },
                          "human": {
                            "type": "object",
                            "properties": {
                              "source": {
                                "type": "boolean"
                              },
                              "rating": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "source",
                              "rating"
                            ]
                          },
                          "coopertition": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "auto",
                          "comments",
                          "competition",
                          "defensive",
                          "matchNumber",
                          "penaltyPointsEarned",
                          "ranking",
                          "rankingPoints",
                          "score",
                          "stage",
                          "teamNumber",
                          "teleop",
                          "tied",
                          "won",
                          "brokeDown",
                          "teamName",
                          "createdAt",
                          "updatedAt",
                          "_id",
                          "createdBy",
                          "rating",
                          "defenseRating",
                          "human",
                          "coopertition"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "auto": {
                            "type": "object",
                            "properties": {
                              "leave": {
                                "type": "boolean"
                              },
                              "attempted": {
                                "type": "number"
                              },
                              "scored": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "leave",
                              "attempted",
                              "scored"
                            ]
                          },
                          "comments": {
                            "type": "string"
                          },
                          "competition": {
                            "type": "string"
                          },
                          "defensive": {
                            "type": "boolean"
                          },
                          "matchNumber": {
                            "type": "number"
                          },
                          "penaltyPointsEarned": {
                            "type": "number"
                          },
                          "ranking": {
                            "type": "object",
                            "properties": {
                              "melody": {
                                "type": "boolean"
                              },
                              "ensemble": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "melody",
                              "ensemble"
                            ]
                          },
                          "rankingPoints": {
                            "type": "number"
                          },
                          "score": {
                            "type": "number"
                          },
                          "stage": {
                            "type": "object",
                            "properties": {
                              "state": {
                                "anyOf": [
                                  {
                                    "const": "NOT_PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE_SPOTLIT",
                                    "type": "string"
                                  }
                                ]
                              },
                              "harmony": {
                                "type": "number"
                              },
                              "trap": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "state",
                              "harmony",
                              "trap"
                            ]
                          },
                          "teamNumber": {
                            "type": "number"
                          },
                          "teleop": {
                            "type": "object",
                            "properties": {
                              "ampNotes": {
                                "type": "number"
                              },
                              "speakerUnamped": {
                                "type": "number"
                              },
                              "speakerAmped": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "ampNotes",
                              "speakerUnamped",
                              "speakerAmped"
                            ]
                          },
                          "tied": {
                            "type": "boolean"
                          },
                          "won": {
                            "type": "boolean"
                          },
                          "brokeDown": {
                            "type": "boolean"
                          },
                          "teamName": {
                            "type": "string"
                          },
                          "createdAt": {
                            "type": "string"
                          },
                          "updatedAt": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "rating": {
                            "type": "number"
                          },
                          "defenseRating": {
                            "type": "number"
                          },
                          "human": {
                            "type": "object",
                            "properties": {
                              "source": {
                                "type": "boolean"
                              },
                              "rating": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "source",
                              "rating"
                            ]
                          },
                          "coopertition": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "auto",
                          "comments",
                          "competition",
                          "defensive",
                          "matchNumber",
                          "penaltyPointsEarned",
                          "ranking",
                          "rankingPoints",
                          "score",
                          "stage",
                          "teamNumber",
                          "teleop",
                          "tied",
                          "won",
                          "brokeDown",
                          "teamName",
                          "createdAt",
                          "updatedAt",
                          "_id",
                          "createdBy",
                          "rating",
                          "defenseRating",
                          "human",
                          "coopertition"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "auto": {
                            "type": "object",
                            "properties": {
                              "leave": {
                                "type": "boolean"
                              },
                              "attempted": {
                                "type": "number"
                              },
                              "scored": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "leave",
                              "attempted",
                              "scored"
                            ]
                          },
                          "comments": {
                            "type": "string"
                          },
                          "competition": {
                            "type": "string"
                          },
                          "defensive": {
                            "type": "boolean"
                          },
                          "matchNumber": {
                            "type": "number"
                          },
                          "penaltyPointsEarned": {
                            "type": "number"
                          },
                          "ranking": {
                            "type": "object",
                            "properties": {
                              "melody": {
                                "type": "boolean"
                              },
                              "ensemble": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "melody",
                              "ensemble"
                            ]
                          },
                          "rankingPoints": {
                            "type": "number"
                          },
                          "score": {
                            "type": "number"
                          },
                          "stage": {
                            "type": "object",
                            "properties": {
                              "state": {
                                "anyOf": [
                                  {
                                    "const": "NOT_PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "PARKED",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE",
                                    "type": "string"
                                  },
                                  {
                                    "const": "ONSTAGE_SPOTLIT",
                                    "type": "string"
                                  }
                                ]
                              },
                              "harmony": {
                                "type": "number"
                              },
                              "trap": {
                                "type": "boolean"
                              }
                            },
                            "required": [
                              "state",
                              "harmony",
                              "trap"
                            ]
                          },
                          "teamNumber": {
                            "type": "number"
                          },
                          "teleop": {
                            "type": "object",
                            "properties": {
                              "ampNotes": {
                                "type": "number"
                              },
                              "speakerUnamped": {
                                "type": "number"
                              },
                              "speakerAmped": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "ampNotes",
                              "speakerUnamped",
                              "speakerAmped"
                            ]
                          },
                          "tied": {
                            "type": "boolean"
                          },
                          "won": {
                            "type": "boolean"
                          },
                          "brokeDown": {
                            "type": "boolean"
                          },
                          "teamName": {
                            "type": "string"
                          },
                          "createdAt": {
                            "type": "string"
                          },
                          "updatedAt": {
                            "type": "string"
                          },
                          "_id": {
                            "type": "string"
                          },
                          "createdBy": {
                            "type": "string"
                          },
                          "rating": {
                            "type": "number"
                          },
                          "defenseRating": {
                            "type": "number"
                          },
                          "human": {
                            "type": "object",
                            "properties": {
                              "source": {
                                "type": "boolean"
                              },
                              "rating": {
                                "type": "number"
                              }
                            },
                            "required": [
                              "source",
                              "rating"
                            ]
                          },
                          "coopertition": {
                            "type": "boolean"
                          }
                        },
                        "required": [
                          "auto",
                          "comments",
                          "competition",
                          "defensive",
                          "matchNumber",
                          "penaltyPointsEarned",
                          "ranking",
                          "rankingPoints",
                          "score",
                          "stage",
                          "teamNumber",
                          "teleop",
                          "tied",
                          "won",
                          "brokeDown",
                          "teamName",
                          "createdAt",
                          "updatedAt",
                          "_id",
                          "createdBy",
                          "rating",
                          "defenseRating",
                          "human",
                          "coopertition"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "404": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getCrescendoMine",
            "description": "Get all scouting submissions for the Crescendo game submitted by the logged in user.",
            "tags": [
              "Crescendo",
              "Scouting"
            ]
          }
        },
        "/crescendo/{id}": {
          "delete": {
            "operationId": "deleteCrescendoById",
            "tags": [
              "Crescendo",
              "Scouting"
            ],
            "parameters": [
              {
                "schema": {
                  "type": "string"
                },
                "in": "path",
                "name": "id",
                "required": true
              }
            ],
            "responses": {
              "200": {

              }
            }
          }
        },
        "/inpit/": {
          "post": {
            "parameters": [],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "teamNumber": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "data": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "value": {
                                  "type": "string"
                                },
                                "lastUpdatedAt": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "lastUpdatedBy": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "value",
                                "lastUpdatedAt",
                                "lastUpdatedBy"
                              ]
                            }
                          }
                        }
                      },
                      "required": [
                        "teamNumber",
                        "data"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "teamNumber": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "data": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "value": {
                                  "type": "string"
                                },
                                "lastUpdatedAt": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "lastUpdatedBy": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "value",
                                "lastUpdatedAt",
                                "lastUpdatedBy"
                              ]
                            }
                          }
                        }
                      },
                      "required": [
                        "teamNumber",
                        "data"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "teamNumber": {
                          "anyOf": [
                            {
                              "format": "numeric",
                              "default": 0,
                              "type": "string"
                            },
                            {
                              "type": "number"
                            }
                          ]
                        },
                        "data": {
                          "type": "object",
                          "patternProperties": {
                            "^(.*)${'$'}": {
                              "type": "object",
                              "properties": {
                                "value": {
                                  "type": "string"
                                },
                                "lastUpdatedAt": {
                                  "anyOf": [
                                    {
                                      "format": "numeric",
                                      "default": 0,
                                      "type": "string"
                                    },
                                    {
                                      "type": "number"
                                    }
                                  ]
                                },
                                "lastUpdatedBy": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "value",
                                "lastUpdatedAt",
                                "lastUpdatedBy"
                              ]
                            }
                          }
                        }
                      },
                      "required": [
                        "teamNumber",
                        "data"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "postInpit",
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "teamNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "data": {
                        "type": "object",
                        "properties": {

                        }
                      },
                      "unset": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "required": [
                      "teamNumber",
                      "data"
                    ],
                    "additionalProperties": false
                  }
                },
                "multipart/form-data": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "teamNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "data": {
                        "type": "object",
                        "properties": {

                        }
                      },
                      "unset": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "required": [
                      "teamNumber",
                      "data"
                    ],
                    "additionalProperties": false
                  }
                },
                "text/plain": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "teamNumber": {
                        "anyOf": [
                          {
                            "format": "numeric",
                            "default": 0,
                            "type": "string"
                          },
                          {
                            "type": "number"
                          }
                        ]
                      },
                      "data": {
                        "type": "object",
                        "properties": {

                        }
                      },
                      "unset": {
                        "type": "array",
                        "items": {
                          "type": "string"
                        }
                      }
                    },
                    "required": [
                      "teamNumber",
                      "data"
                    ],
                    "additionalProperties": false
                  }
                }
              }
            }
          },
          "get": {
            "responses": {
              "200": {
                "items": {
                  "type": "object",
                  "properties": {
                    "teamNumber": {
                      "anyOf": [
                        {
                          "format": "numeric",
                          "default": 0,
                          "type": "string"
                        },
                        {
                          "type": "number"
                        }
                      ]
                    },
                    "data": {
                      "type": "object",
                      "patternProperties": {
                        "^(.*)${'$'}": {
                          "type": "object",
                          "properties": {
                            "value": {
                              "type": "string"
                            },
                            "lastUpdatedAt": {
                              "anyOf": [
                                {
                                  "format": "numeric",
                                  "default": 0,
                                  "type": "string"
                                },
                                {
                                  "type": "number"
                                }
                              ]
                            },
                            "lastUpdatedBy": {
                              "type": "string"
                            }
                          },
                          "required": [
                            "value",
                            "lastUpdatedAt",
                            "lastUpdatedBy"
                          ]
                        }
                      }
                    }
                  },
                  "required": [
                    "teamNumber",
                    "data"
                  ],
                  "additionalProperties": false
                },
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "teamNumber": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "data": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "value": {
                                    "type": "string"
                                  },
                                  "lastUpdatedAt": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "lastUpdatedBy": {
                                    "type": "string"
                                  }
                                },
                                "required": [
                                  "value",
                                  "lastUpdatedAt",
                                  "lastUpdatedBy"
                                ]
                              }
                            }
                          }
                        },
                        "required": [
                          "teamNumber",
                          "data"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "teamNumber": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "data": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "value": {
                                    "type": "string"
                                  },
                                  "lastUpdatedAt": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "lastUpdatedBy": {
                                    "type": "string"
                                  }
                                },
                                "required": [
                                  "value",
                                  "lastUpdatedAt",
                                  "lastUpdatedBy"
                                ]
                              }
                            }
                          }
                        },
                        "required": [
                          "teamNumber",
                          "data"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "teamNumber": {
                            "anyOf": [
                              {
                                "format": "numeric",
                                "default": 0,
                                "type": "string"
                              },
                              {
                                "type": "number"
                              }
                            ]
                          },
                          "data": {
                            "type": "object",
                            "patternProperties": {
                              "^(.*)${'$'}": {
                                "type": "object",
                                "properties": {
                                  "value": {
                                    "type": "string"
                                  },
                                  "lastUpdatedAt": {
                                    "anyOf": [
                                      {
                                        "format": "numeric",
                                        "default": 0,
                                        "type": "string"
                                      },
                                      {
                                        "type": "number"
                                      }
                                    ]
                                  },
                                  "lastUpdatedBy": {
                                    "type": "string"
                                  }
                                },
                                "required": [
                                  "value",
                                  "lastUpdatedAt",
                                  "lastUpdatedBy"
                                ]
                              }
                            }
                          }
                        },
                        "required": [
                          "teamNumber",
                          "data"
                        ],
                        "additionalProperties": false
                      }
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "getInpit"
          }
        },
        "/inpit/{team}": {
          "delete": {
            "parameters": [
              {
                "anyOf": [
                  {
                    "format": "numeric",
                    "default": 0,
                    "type": "string"
                  },
                  {
                    "type": "number"
                  }
                ],
                "schema": {

                },
                "in": "path",
                "name": "team",
                "required": true
              }
            ],
            "responses": {
              "200": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              },
              "500": {
                "content": {
                  "application/json": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "multipart/form-data": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  },
                  "text/plain": {
                    "schema": {
                      "type": "object",
                      "properties": {
                        "message": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "message"
                      ],
                      "additionalProperties": false
                    }
                  }
                }
              }
            },
            "operationId": "deleteInpitByTeam"
          }
        }
      },
      "components": {
        "schemas": {

        }
      }
    }
""".trimIndent()
)